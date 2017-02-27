package com.cx.plugin.dto;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import static com.cx.plugin.dto.CxParam.OPTION_FALSE;
import static com.cx.plugin.dto.CxParam.OPTION_TRUE;
import static com.cx.plugin.dto.CxResultsConst.*;

/**
 * Created by Galn on 23/02/2017.
 */
public abstract class ResultUtils {
    private static final Logger log = LoggerFactory.getLogger(ResultUtils.class);

    public static String resolveCostumeBuildData(Map<String, String> costumeBuildData) {
        String ret = "";
        String resultsTemplate = getResultsTemplate();
        if (costumeBuildData.get(SAST_RESULTS_READY) != null || costumeBuildData.get(OSA_RESULTS_READY) != null) {
            if (resultsTemplate != null) {

                ret = resultsTemplate
                        .replaceAll(HIGH_RESULTS, String.valueOf(costumeBuildData.get(HIGH_RESULTS)))
                        .replace(MEDIUM_RESULTS, String.valueOf(costumeBuildData.get(MEDIUM_RESULTS)))
                        .replace(LOW_RESULTS, String.valueOf(costumeBuildData.get(LOW_RESULTS)))
                        .replace(SAST_SUMMARY_RESULTS_LINK, String.valueOf(costumeBuildData.get(SAST_SUMMARY_RESULTS_LINK)))
                        .replace(THRESHOLD_ENABLED, String.valueOf(costumeBuildData.get(THRESHOLD_ENABLED)))
                        .replace(HIGH_THRESHOLD, String.valueOf(costumeBuildData.get(HIGH_THRESHOLD)))
                        .replace(MEDIUM_THRESHOLD, String.valueOf(costumeBuildData.get(MEDIUM_THRESHOLD)))
                        .replace(LOW_THRESHOLD, String.valueOf(costumeBuildData.get(LOW_THRESHOLD)));


                if (costumeBuildData.get(OSA_RESULTS_READY) != null) {
                    ret = ret
                            .replace(OSA_ENABLED, OPTION_TRUE)
                            .replace(OSA_HIGH_RESULTS, String.valueOf(costumeBuildData.get(OSA_HIGH_RESULTS)))
                            .replace(OSA_MEDIUM_RESULTS, String.valueOf(costumeBuildData.get(OSA_MEDIUM_RESULTS)))
                            .replace(OSA_LOW_RESULTS, String.valueOf(costumeBuildData.get(OSA_LOW_RESULTS)))
                            .replace(OSA_SUMMARY_RESULTS_LINK, String.valueOf(costumeBuildData.get(OSA_SUMMARY_RESULTS_LINK)))
                            .replace(OSA_THRESHOLD_ENABLED, String.valueOf(costumeBuildData.get(OSA_THRESHOLD_ENABLED)))
                            .replace(OSA_HIGH_THRESHOLD, String.valueOf(costumeBuildData.get(OSA_HIGH_THRESHOLD)))
                            .replace(OSA_MEDIUM_THRESHOLD, String.valueOf(costumeBuildData.get(OSA_MEDIUM_THRESHOLD)))
                            .replace(OSA_LOW_THRESHOLD, String.valueOf(costumeBuildData.get(OSA_LOW_THRESHOLD)))
                            .replace(OSA_VULNERABLE_LIBRARIES, String.valueOf(costumeBuildData.get(OSA_VULNERABLE_LIBRARIES)))
                            .replace(OSA_OK_LIBRARIES, String.valueOf(costumeBuildData.get(OSA_OK_LIBRARIES)));
                } else {
                    ret = ret
                            .replace(OSA_ENABLED, OPTION_FALSE)
                            .replace(OSA_HIGH_RESULTS, "0")
                            .replace(OSA_MEDIUM_RESULTS, "0")
                            .replace(OSA_LOW_RESULTS, "0")
                            .replace(OSA_SUMMARY_RESULTS_LINK, "")
                            .replace(OSA_THRESHOLD_ENABLED, "false")
                            .replace(OSA_HIGH_THRESHOLD, "0")
                            .replace(OSA_MEDIUM_THRESHOLD, "0")
                            .replace(OSA_LOW_THRESHOLD, "0")
                            .replace(OSA_VULNERABLE_LIBRARIES, "0")
                            .replace(OSA_OK_LIBRARIES, "0");
                }
            }

        } else {
            return "";
        }

        return ret;
    }

    private static String getResultsTemplate() {
        String ret = null;
        InputStream resourceAsStream = ResultUtils.class.getResourceAsStream("/com/cx/plugin/resultsTemplate.html");
        if (resourceAsStream != null) {
            try {
                ret = IOUtils.toString(resourceAsStream, Charset.defaultCharset().name());
            } catch (IOException e) {
                log.warn("fail to get results template", e.getMessage());
            }
        }

        return ret;
    }
}
