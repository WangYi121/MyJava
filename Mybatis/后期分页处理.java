 //对结果分页
        Integer pageSize = searchCondition.getPageSize();
        Integer pageNum = searchCondition.getPageNow();
        if (pageSize == 0 && pageNum == 0) {
            result.setImportLists(importLists);
        }
        Integer start = pageSize * (pageNum - 1);
        List<DataImportVo> smallList = new ArrayList<>();
        if (start >= importLists.size()) {
            result.setImportLists(smallList);
        }
        int number = importLists.size() - start - pageSize > 0 ? pageSize : importLists.size() - start;
        smallList = importLists.subList(start, start + number);
        result.setImportLists(smallList);
        result.setTotalCount(importLists.size());