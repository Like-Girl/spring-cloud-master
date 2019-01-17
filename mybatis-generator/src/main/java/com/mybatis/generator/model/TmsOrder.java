package com.mybatis.generator.model;

import java.math.BigDecimal;
import java.util.Date;

public class TmsOrder {
    private Long orderId;

    private String orderNo;

    private String customerNo;

    private Long customerId;

    private String customerName;

    private Long projectId;

    private String projectName;

    private Long pickupId;

    private String pickupProvince;

    private String pickupCity;

    private String pickupArea;

    private String pickupAddress;

    private String pickupUser;

    private String pickupTel;

    private Long destinationId;

    private String destinationProvince;

    private String destinationCity;

    private String destinationArea;

    private String destinationAddress;

    private String destinationUser;

    private String destinationTel;

    private Date pickupDatetime;

    private Date plannedCompletionDatetime;

    private Date deliverDatetime;

    private String receiptedType;

    private Date gmtCreated;

    private Integer userCreated;

    private Date gmtModified;

    private Integer userModified;

    private Integer isDeleted;

    private String status;

    private String receivedPhoto;

    private Date waybillDatetime;

    private Integer waybillUser;

    private Date actualPickupDatetime;

    private Integer actualPickupUser;

    private Date actualOkDatetime;

    private Integer actualOkUser;

    private String billingMethod;

    private BigDecimal cost;

    private String plateNumber;

    private String driverName;

    private String typeTemp;

    private BigDecimal minTemp;

    private BigDecimal maxTemp;

    private String temperatureControl;

    private String productName;

    private String vehicleType;

    private Integer lineId;

    private String orderLine;

    private String orderRemarks;

    private Byte orderType;

    private Long companyId;

    private Byte originate;

    private Long parentId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Long getPickupId() {
        return pickupId;
    }

    public void setPickupId(Long pickupId) {
        this.pickupId = pickupId;
    }

    public String getPickupProvince() {
        return pickupProvince;
    }

    public void setPickupProvince(String pickupProvince) {
        this.pickupProvince = pickupProvince == null ? null : pickupProvince.trim();
    }

    public String getPickupCity() {
        return pickupCity;
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity = pickupCity == null ? null : pickupCity.trim();
    }

    public String getPickupArea() {
        return pickupArea;
    }

    public void setPickupArea(String pickupArea) {
        this.pickupArea = pickupArea == null ? null : pickupArea.trim();
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress == null ? null : pickupAddress.trim();
    }

    public String getPickupUser() {
        return pickupUser;
    }

    public void setPickupUser(String pickupUser) {
        this.pickupUser = pickupUser == null ? null : pickupUser.trim();
    }

    public String getPickupTel() {
        return pickupTel;
    }

    public void setPickupTel(String pickupTel) {
        this.pickupTel = pickupTel == null ? null : pickupTel.trim();
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationProvince() {
        return destinationProvince;
    }

    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince == null ? null : destinationProvince.trim();
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity == null ? null : destinationCity.trim();
    }

    public String getDestinationArea() {
        return destinationArea;
    }

    public void setDestinationArea(String destinationArea) {
        this.destinationArea = destinationArea == null ? null : destinationArea.trim();
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress == null ? null : destinationAddress.trim();
    }

    public String getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(String destinationUser) {
        this.destinationUser = destinationUser == null ? null : destinationUser.trim();
    }

    public String getDestinationTel() {
        return destinationTel;
    }

    public void setDestinationTel(String destinationTel) {
        this.destinationTel = destinationTel == null ? null : destinationTel.trim();
    }

    public Date getPickupDatetime() {
        return pickupDatetime;
    }

    public void setPickupDatetime(Date pickupDatetime) {
        this.pickupDatetime = pickupDatetime;
    }

    public Date getPlannedCompletionDatetime() {
        return plannedCompletionDatetime;
    }

    public void setPlannedCompletionDatetime(Date plannedCompletionDatetime) {
        this.plannedCompletionDatetime = plannedCompletionDatetime;
    }

    public Date getDeliverDatetime() {
        return deliverDatetime;
    }

    public void setDeliverDatetime(Date deliverDatetime) {
        this.deliverDatetime = deliverDatetime;
    }

    public String getReceiptedType() {
        return receiptedType;
    }

    public void setReceiptedType(String receiptedType) {
        this.receiptedType = receiptedType == null ? null : receiptedType.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Integer getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Integer userCreated) {
        this.userCreated = userCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getUserModified() {
        return userModified;
    }

    public void setUserModified(Integer userModified) {
        this.userModified = userModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getReceivedPhoto() {
        return receivedPhoto;
    }

    public void setReceivedPhoto(String receivedPhoto) {
        this.receivedPhoto = receivedPhoto == null ? null : receivedPhoto.trim();
    }

    public Date getWaybillDatetime() {
        return waybillDatetime;
    }

    public void setWaybillDatetime(Date waybillDatetime) {
        this.waybillDatetime = waybillDatetime;
    }

    public Integer getWaybillUser() {
        return waybillUser;
    }

    public void setWaybillUser(Integer waybillUser) {
        this.waybillUser = waybillUser;
    }

    public Date getActualPickupDatetime() {
        return actualPickupDatetime;
    }

    public void setActualPickupDatetime(Date actualPickupDatetime) {
        this.actualPickupDatetime = actualPickupDatetime;
    }

    public Integer getActualPickupUser() {
        return actualPickupUser;
    }

    public void setActualPickupUser(Integer actualPickupUser) {
        this.actualPickupUser = actualPickupUser;
    }

    public Date getActualOkDatetime() {
        return actualOkDatetime;
    }

    public void setActualOkDatetime(Date actualOkDatetime) {
        this.actualOkDatetime = actualOkDatetime;
    }

    public Integer getActualOkUser() {
        return actualOkUser;
    }

    public void setActualOkUser(Integer actualOkUser) {
        this.actualOkUser = actualOkUser;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod == null ? null : billingMethod.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public String getTypeTemp() {
        return typeTemp;
    }

    public void setTypeTemp(String typeTemp) {
        this.typeTemp = typeTemp == null ? null : typeTemp.trim();
    }

    public BigDecimal getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(BigDecimal minTemp) {
        this.minTemp = minTemp;
    }

    public BigDecimal getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(BigDecimal maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getTemperatureControl() {
        return temperatureControl;
    }

    public void setTemperatureControl(String temperatureControl) {
        this.temperatureControl = temperatureControl == null ? null : temperatureControl.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(String orderLine) {
        this.orderLine = orderLine == null ? null : orderLine.trim();
    }

    public String getOrderRemarks() {
        return orderRemarks;
    }

    public void setOrderRemarks(String orderRemarks) {
        this.orderRemarks = orderRemarks == null ? null : orderRemarks.trim();
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Byte getOriginate() {
        return originate;
    }

    public void setOriginate(Byte originate) {
        this.originate = originate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}