	/**
     * 假分页
     */
    protected TableDataInfo getNewDataTable(Integer pageNum, Integer pageSize, List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        if (list == null) {
            rspData.setRows(list);
            rspData.setTotal(0);
            return rspData;
        }
        if (pageNum == null || pageSize == null) {
            rspData.setRows(list);
            rspData.setTotal(list.size());
            return rspData;
        }
        // 分页逻辑
        Integer start = pageSize * (pageNum - 1);
        if (start >= list.size()) {
            rspData.setRows(Collections.EMPTY_LIST);
            rspData.setTotal(0);
            return rspData;
        }
        Integer end = pageSize * pageNum;
        if (end >= list.size()) {
            end = list.size();
        }
        rspData.setTotal(list.size());
        rspData.setRows(list.subList(start, end));
        return rspData;
    }