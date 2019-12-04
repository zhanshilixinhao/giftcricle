package com.chouchong.common.v3;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>项目名称</b>：lanrenxiyi<br>
 * <b>类名称</b>：ExcelUtils<br>
 * <b>类描述</b>：项目Excel导入导出工具类<br>
 * <b>创建人</b>：SAM QZL<br>
 * <b>创建时间</b>：2015-11-16 下午1:42:32<br>
 * <b>修改人</b>：SAM QZL<br>
 * <b>修改时间</b>：2015-11-16 下午1:42:32<br>
 * <b>修改备注</b>：<br>
 */
public class ExcelUtils<T> {

    /**
     * 导出数据
     *
     * @param request
     * @param response
     */
    public static void preExport(HttpServletRequest request, HttpServletResponse response, String name) throws UnsupportedEncodingException {
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        /** 获取浏览器类别 **/
        String agent = request.getHeader("USER-AGENT");
        String fileName = "";
        /** IE **/
        if (null != agent && agent.contains("MSIE")) {
            fileName = URLEncoder.encode(name + "-" + getToday()
                    + ".xlsx", "utf-8");
            fileName = fileName.replace("+", "%20");// 处理空格变“+”的问题
        }
        /** FF **/
        else if (null != agent && agent.contains("Mozilla")) {
            String tname = name + "-" + getToday() + ".xlsx";
            fileName = new String(tname.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else {
            fileName = URLEncoder.encode(name + "-" + getToday()
                    + ".xlsx", "utf-8");
        }
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    private static String getToday() {
        return new SimpleDateFormat("YYYYmmdd").format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getToday());
    }

    public static class Header {
        public String name;
        public String title;

        public static Header h(String name, String title) {
            Header h = new Header();
            h.name = name;
            h.title = title;
            return h;
        }
    }

    /**
     * <b>exportExcel</b>：(根据传入集合数据,导出excel表格输出件流.)<br>
     *
     * @param title   ：excel标题<br>
     * @param headers ：表头名称字符串数组<br>
     * @param dataset ：数据集合<br>
     * @param out     ：输出流<br>
     * @param pattern ：日期格式 如"yyyy-MM-dd HH:mm:ss"<br>
     * @return void<br>
     * @Exception<br>
     */
    public static <T> void exportExcel2(String title, Header[] headers, Collection<T> dataset, OutputStream out, String pattern) {

        XSSFWorkbook workbook2007 = new XSSFWorkbook();
        /** 工作目录创建 **/
        XSSFSheet sheet = workbook2007.createSheet(title);

        /** 设置默认宽度 **/
        sheet.setDefaultColumnWidth(15);
        /** 这只表头单元格格式 **/
        CellStyle titleCellStyle = workbook2007.createCellStyle();
        /** 设置样式 **/
        titleCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        titleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        /** 创建表头字体 **/
        Font titleFont = workbook2007.createFont();
        /** 设置字体样式 **/
        titleFont.setColor(HSSFColor.VIOLET.index);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        /** 字体应用到表头 **/
        titleCellStyle.setFont(titleFont);
        /** 设置内容样式 **/
        CellStyle contentCellStyle = workbook2007.createCellStyle();
        contentCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        contentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        contentCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        contentCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        /** 设置内容自动换行 **/
        contentCellStyle.setWrapText(true);
        /** 设置内容字体 **/
        Font contentFont = workbook2007.createFont();
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        /** 设置字体 **/
        contentCellStyle.setFont(contentFont);
        /** 创建标题 **/
        XSSFRow titleRow = sheet.createRow(0);
        /** 设置宽度最后一列宽度大 **/
        sheet.setColumnWidth(headers.length - 1, 100 * 256);
        /** 生成表头 **/
        for (int i = 0; i < headers.length; i++) {
            /** 创建表头单元格 **/
            XSSFCell cell = titleRow.createCell(i);
            /** 样式应用 **/
            cell.setCellStyle(titleCellStyle);
            RichTextString text = new XSSFRichTextString(headers[i].title);
            cell.setCellValue(text);
        }

        /** 内容生成 **/
        Iterator<T> it = dataset.iterator();
        /** 行索引 --从1开始 **/
        int index = 1;
        List<Me> list = new ArrayList<>();
        Me me = null;
        int i = 0;
        while (it.hasNext()) {
            /** 创建内容行 **/
            XSSFRow contentRow = sheet.createRow(index);
            /** 获取当前行记录 **/
            T t = it.next();
            // 如果是map类型
            if (t instanceof Map) {
                Map map = (Map) t;
                int j = 0;
                for (Header header : headers) {
                    Object value = map.get(header.name);
                    fillData(value, pattern, contentRow, j++, contentCellStyle, workbook2007);
                }
                if (map.containsKey("id")) {
                    if (me == null) {
                        me = new Me();
                        me.start = index;
                        me.id = map.get("id").toString();
                    } else {
                        if (StringUtils.isNotBlank(me.id) && !StringUtils.equals(me.id, map.get("id").toString())) {
                            me.end = index-1;
                            list.add(me);
                            me = new Me();
                            me.start = index;
                            me.id = map.get("id").toString();
                        }
                    }
                }
            } else { // 对象类型
                /** 获取字段名称 **/
                Field[] fields = t.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    String fieldName = field.getName();
                    /** 获取get方法的名称 **/
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    try {
                        Method method = t.getClass().getMethod(getMethodName);
                        Object value = method.invoke(t);
                        fillData(value, pattern, contentRow, j, contentCellStyle, workbook2007);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            index++;
        }
        if (!CollectionUtils.isEmpty(list)) {
            for (Me itt : list) {
                if (itt.start != itt.end) {
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 0, 0));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 1, 1));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 2, 2));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 3, 3));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 4, 4));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 5, 5));
                    sheet.addMergedRegion(new CellRangeAddress(itt.start, itt.end, 12, 12));
                }
            }
        }
        /** 写到输出流 **/
        try {
            workbook2007.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook2007.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Me {
        int start;
        String id;
        int end;
    }

    private static void fillData(Object value, String pattern, XSSFRow contentRow, int j, CellStyle contentCellStyle, XSSFWorkbook workbook2007) {
        /** 创建内容列 **/
        XSSFCell contentCell = contentRow.createCell(j);
        /** 设置样式 **/
        contentCell.setCellStyle(contentCellStyle);
        String finalText = "";
        /** 如果是日期类型格式化输出 **/
        if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            finalText = sdf.format(date);
        }
        /** 其余都是tostring **/
        else {
            finalText = value == null ? "" : value.toString();
        }
        /** 处理finalText **/
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher = p.matcher(finalText);
        /** 如果是小数单独处理 **/
        if (matcher.matches()) {
            contentCell.setCellValue(Double.parseDouble(finalText));
        }
        /** 普通字符串 **/
        else {
            XSSFRichTextString richString = new XSSFRichTextString(finalText);
            Font font_ = workbook2007.createFont();
            font_.setColor(HSSFColor.BLACK.index);
            richString.applyFont(font_);
            contentCell.setCellValue(richString);
        }
    }

    /**
     * <b>exportExcel</b>：(根据传入集合数据,导出excel表格输出件流.)<br>
     *
     * @param title   ：excel标题<br>
     * @param headers ：表头名称字符串数组<br>
     * @param dataset ：数据集合<br>
     * @param out     ：输出流<br>
     * @param pattern ：日期格式 如"yyyy-MM-dd HH:mm:ss"<br>
     * @return void<br>
     * @Exception<br>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Deprecated
    public static <T> void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth((short) 15);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setWrapText(true);// 先设置为自动换行
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        comment.setString(new HSSFRichTextString("添加注释！"));
        comment.setAuthor("");
        HSSFRow row = sheet.createRow(0);
        sheet.setColumnWidth(headers.length - 1, 100 * 256);// 最后一列宽度大
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = "";
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        row.setHeightInPoints(60);
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLACK.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
