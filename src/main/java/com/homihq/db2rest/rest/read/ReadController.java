package com.homihq.db2rest.rest.read;

import com.homihq.db2rest.rest.read.dto.FindOneResponse;
import com.homihq.db2rest.rest.read.dto.QueryRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.web.bind.ServletRequestUtils.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ReadController {

    private final ReadService readService;

    @GetMapping(value = "/{tableName}" , produces = "application/json")
    public Object findByJoinTable(@PathVariable String tableName,
                                  @RequestHeader(name = "Accept-Profile", required = false) String schemaName,
                                  @RequestParam(name = "select", required = false, defaultValue = "") String select,
                                  @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                                  Sort sort,
                                  Pageable pageable, HttpServletRequest httpServletRequest) {

        log.info("schemaName - {}", schemaName);
        log.info("select - {}", select);
        log.info("filter - {}", filter);
        log.info("pageable - {}", pageable);


        if( getIntParameter(httpServletRequest, "page", -1) == -1 &&
                getIntParameter(httpServletRequest, "size", -1) == -1) {
            pageable = Pageable.unpaged(sort);
        }


        return readService.findAll(schemaName, tableName,select, filter, pageable, sort);
    }

    @GetMapping("/{tableName}/one")
    public FindOneResponse findOne(@PathVariable String tableName,
                                   @RequestParam(name = "select", required = false, defaultValue = "") String select,
                                   @RequestParam(name = "filter", required = false, defaultValue = "") String filter) {


        log.info("tableName - {}", tableName);
        log.info("select - {}", select);
        log.info("filter - {}", filter);

        return this.readService.findOne(tableName, select, filter);
    }


}
