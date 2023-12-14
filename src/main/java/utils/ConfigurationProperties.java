package utils;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//Sostituisce FrameworkProperties per il BDD
@Component
@PropertySource("framework.properties")
public class ConfigurationProperties {
    @value("$(browser)")
    private String browser;

    @value("$(standard_user)")
    private String standard_user;

    @value("$(locked_out_user)")
    private String locked_out_user;

    @value("$(problem_user)")
    private String problem_user;

    @value("$(performance_glitch_user)")
    private String performance_glitch_user;

    @value("$(error_user)")
    private String error_user;

    @value("$(visual_user)")
    private String visual_user;

    @value("$(wrong_credential)")
    private String wrong_credential;

    @value("$(locked_out_message)")
    private String locked_out_message;

    @value("$(wrongCredential_message)")
    private String wrongCredential_message;

    @value("$(password)")
    private String password;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getStandard_user() {
        return standard_user;
    }

    public void setStandard_user(String standard_user) {
        this.standard_user = standard_user;
    }

    public String getLocked_out_user() {
        return locked_out_user;
    }

    public void setLocked_out_user(String locked_out_user) {
        this.locked_out_user = locked_out_user;
    }

    public String getProblem_user() {
        return problem_user;
    }

    public void setProblem_user(String problem_user) {
        this.problem_user = problem_user;
    }

    public String getPerformance_glitch_user() {
        return performance_glitch_user;
    }

    public void setPerformance_glitch_user(String performance_glitch_user) {
        this.performance_glitch_user = performance_glitch_user;
    }

    public String getError_user() {
        return error_user;
    }

    public void setError_user(String error_user) {
        this.error_user = error_user;
    }

    public String getVisual_user() {
        return visual_user;
    }

    public void setVisual_user(String visual_user) {
        this.visual_user = visual_user;
    }

    public String getWrong_credential() {
        return wrong_credential;
    }

    public void setWrong_credential(String wrong_credential) {
        this.wrong_credential = wrong_credential;
    }

    public String getLocked_out_message() {
        return locked_out_message;
    }

    public void setLocked_out_message(String locked_out_message) {
        this.locked_out_message = locked_out_message;
    }

    public String getWrongCredential_message() {
        return wrongCredential_message;
    }

    public void setWrongCredential_message(String wrongCredential_message) {
        this.wrongCredential_message = wrongCredential_message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
