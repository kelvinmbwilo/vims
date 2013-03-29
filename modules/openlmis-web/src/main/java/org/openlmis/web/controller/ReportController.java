package org.openlmis.web.controller;

import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openlmis.core.domain.Facility;
import org.openlmis.report.Report;
import org.openlmis.report.ReportManager;
import org.openlmis.report.ReportOutputOption;
import org.openlmis.report.model.FacilityReport;
import org.openlmis.report.model.Pages;
import org.openlmis.report.model.ReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 */
@Controller
@NoArgsConstructor
@RequestMapping(value = "/reports")
public class ReportController  extends BaseController {

    public static final String USER_ID = "USER_ID";

    private ReportManager reportManager;
    @Autowired
    public ReportController(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    @RequestMapping(value = "/download/{reportKey}/{outputOption}")
    public void showReport(@PathVariable(value = "reportKey") String reportKey, @PathVariable(value = "outputOption") String outputOption,ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){

       Integer userId = (Integer) request.getSession().getAttribute(USER_ID);

        switch (outputOption.toUpperCase()){
            case "PDF":
                reportManager.showReport(userId, reportKey, null, ReportOutputOption.PDF, response);
                break;
            case "XLS":
                reportManager.showReport(userId, reportKey, null, ReportOutputOption.XLS, response);
        }

    }

    @RequestMapping(value = "/reportdata/{reportKey}", method = GET, headers = ACCEPT_JSON)
    @PreAuthorize("@permissionEvaluator.hasPermission(principal,'MANAGE_FACILITY')")
    public Pages get(@PathVariable(value = "reportKey") String reportKey,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(value = "max", required = false, defaultValue = "20") int max) {

        //Pageable pageRequest = new PageRequest(page-1, max);
        Report report = reportManager.getReportByKey(reportKey);
        List<FacilityReport> facilityReportList = (List<FacilityReport>) report.getReportDataProvider().getReportDataByFilterCriteria(null);
        final int startIdx = (page - 1) * max;
        final int endIdx = Math.min(startIdx + max, facilityReportList.size());
        //List<FacilityReport> facilityReportListJson =  (FacilityReport)facilityReportList;
        return new Pages(page,facilityReportList.size(),max,facilityReportList.subList(startIdx,endIdx));
    }

}
