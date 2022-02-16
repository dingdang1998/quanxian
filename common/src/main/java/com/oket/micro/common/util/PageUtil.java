package com.oket.micro.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hjt
 * @Description:自定义List分页方法
 * list 需要分页的list
 * pageNum 页码
 * pageSize 每页需要多少数据
 * @Modified By:
 */
public class PageUtil {
    /**
     * 默认行数
     */
    private final static int defaultPageRow=10;

    /**
     * 自定义分页
     * @param list
     * @param pageNum
     * @param pageSize
     * @param <obj>
     * @return
     */
    public static <obj> List<obj> startPage(List<obj> list, Integer pageNum, Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return list;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List<obj> pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }

    /**
     * 入参添加分页参数返回map
     * @param pageNum
     * @param pageRow
     * @return
     */
   /* public static Map<String,Object> pageParamMap(Integer pageNum,Integer pageRow){
        Map<String,Object> pagePageMap=new HashMap<>();
        if(pageNum!=null && pageRow!=null){
            pagePageMap.put("pageNum",pageNum);
            pagePageMap.put("pageRow",pageRow);
        }
        return pagePageMap;
    }*/
    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param pageNum
     * @param pageRow
     */
    public static Map<String,Object> fillPageParam( Integer pageNum, Integer pageRow) {
        Map<String,Object> pagePageMap=new HashMap<>();
        if(pageNum!=null && pageRow!=null){
            pageNum = pageNum == 0 ? 1 : pageNum;
            pageRow = pageRow == 0 ? defaultPageRow : pageRow;
            pagePageMap.put("offSet", (pageNum - 1) * pageRow);
            pagePageMap.put("pageRow", pageRow);
            pagePageMap.put("pageNum", pageNum);
            //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
            pagePageMap.remove("pageSize");
        }
        return pagePageMap;
    }

    /**
     * 列表查询是否分页结果后的封装工具方法
     *
     * @return param list        查询分页对象list
     * @return param totalCount  查询出记录的总条数
     */
    public static Map<String,Object> resultMap(Map<String,Object> param) {
        Map<String,Object> result=new HashMap<>();
        result.put("list",param.get("list"));
        if(param.get("pageRow") !=null && param.get("pageNum") !=null){
            //返回分页查询结果
            int totalCount=Integer.parseInt(param.get("totalCount")+"");
            int pageRow=Integer.parseInt(param.get("pageRow")+"");
            int totalPage = Result.getPageCounts(pageRow, totalCount);
            result.put("totalCount",totalCount);
            result.put("totalPage", totalPage);
        }
        return result;

    }

}
