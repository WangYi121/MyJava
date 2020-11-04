if (CollectionUtils.isEmpty(map) || StringUtil.isEmpty(map.get("topicId"))) {
throw new BizException(ErrorCode.PARAM_ERROR);
}
		
		
		
		
		
if (!StringUtil.isNullOrEmpty(map.get(DashboardConstant.COUNTRYID))) {
countryId = Integer.parseInt(map.get(DashboardConstant.COUNTRYID));
}