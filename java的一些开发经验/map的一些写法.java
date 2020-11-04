    不同类型的如何map：
	
	
	@RequestMapping(value = "/getCompletionAndOutput", method = RequestMethod.POST)
    public Result getCompletionAndOutput(@RequestBody Map map) {
        String beginTime = (String) map.get("beginTime");
        String endTime = (String) map.get("endTime");
        int organizationId = Integer.parseInt(map.get("organizationId").toString());
        List<CompletionAndOutputVo> result = completionAndOutputService.getCompletionAndOutput(beginTime, endTime, organizationId);
        return ResultUtil.success(result);
    }