package com.example.insiderreviewapplication.Model;

/**
 * Model For Getting Values From Firebase (Probability)
 */

public class EmployeeModel {
    String employeeName;
    String employeeId;
    int environmentSatisfaction;
    int jobSatisfaction;
    int relationshipSatisfaction;
    int workLifeBalance;
    boolean attrition;
    double probability;

    public EmployeeModel(String employeeName, String employeeId, int environmentSatisfaction, int jobSatisfaction, int relationshipSatisfaction, int workLifeBalance, boolean attrition, double probability) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.environmentSatisfaction = environmentSatisfaction;
        this.jobSatisfaction = jobSatisfaction;
        this.relationshipSatisfaction = relationshipSatisfaction;
        this.workLifeBalance = workLifeBalance;
        this.attrition = attrition;
        this.probability = probability;
    }

    public EmployeeModel(){

    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public boolean getAttrition() {
        return attrition;
    }

    public void setAttrition(boolean attrition) {
        this.attrition = attrition;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getEnvironmentSatisfaction() {
        return environmentSatisfaction;
    }

    public void setEnvironmentSatisfaction(int environmentSatisfaction) {
        this.environmentSatisfaction = environmentSatisfaction;
    }

    public int getJobSatisfaction() {
        return jobSatisfaction;
    }

    public void setJobSatisfaction(int jobSatisfaction) {
        this.jobSatisfaction = jobSatisfaction;
    }

    public int getRelationshipSatisfaction() {
        return relationshipSatisfaction;
    }

    public void setRelationshipSatisfaction(int relationshipSatisfaction) {
        this.relationshipSatisfaction = relationshipSatisfaction;
    }

    public int getWorkLifeBalance() {
        return workLifeBalance;
    }

    public void setWorkLifeBalance(int workLifeBalance) {
        this.workLifeBalance = workLifeBalance;
    }

}
