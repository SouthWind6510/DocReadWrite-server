package cn.edu.sspu.taskgroup.controller;

import cn.edu.sspu.taskgroup.domain.Student;
import cn.edu.sspu.taskgroup.domain.TaskForm;
import cn.edu.sspu.taskgroup.service.implement.StudentServiceImpl;
import cn.edu.sspu.taskgroup.service.implement.TaskDateServiceImpl;
import cn.edu.sspu.taskgroup.service.implement.TaskFormServiceimpl;
import cn.edu.sspu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.spire.doc.FileFormat;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import org.apache.poi.xwpf.converter.pdf.PdfOptions;
//import org.apache.poi.xwpf.converter.pdf.PdfConverter;


import org.apache.poi.xwpf.usermodel.*;



import java.util.List;

import static java.io.File.separator;


@CrossOrigin
@RestController
@RequestMapping("/taskform")
public class TaskFormController {
    XWPFDocument document = null;
    String fileName = null;
    @Autowired
    TaskFormServiceimpl taskFormServiceimpl;
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    TaskDateServiceImpl taskDateService;
    @Autowired
    HttpServletResponse response;
    StringBuilder sb = new StringBuilder(File.separator);
    String userpath = sb.append("home").append(File.separator).append("wwwserver").append(File.separator).toString();

    @PostMapping("/search")
    public R serarch(@RequestBody Map<String, String> map) {
        QueryWrapper<TaskForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sno", map.get("currentStudentSno"));
        TaskForm checkexist = taskFormServiceimpl.getOne(queryWrapper);
        if (checkexist != null) {
            return R.ok(200, "已有记录", "update");
        } else {
            return R.ok(201, "保存成功", "insert");
        }
    }

    @PostMapping("/gettaskdata")
    public R gettaskform(@RequestBody Map<String, String> map) {
        String currentStudentSno = map.get("currentStudentSno");
        QueryWrapper<TaskForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sno", currentStudentSno);
        TaskForm checkexist = taskFormServiceimpl.getOne(queryWrapper);
        if (checkexist != null) {
            return R.ok(200, "读取记录成功", checkexist);

        } else {
            return R.ok(400, "读取记录失败", "fail");
        }
    }

    @PostMapping("/insert")
    public R insert(@RequestBody TaskForm taskForm) {
        if (taskFormServiceimpl.save(taskForm)) {
            return R.ok(200, "插入成功", taskForm);
        } else
            return R.fail(400, "fail", null);
    }

    @PostMapping("/update")
    public R update(@RequestBody TaskForm taskForm) {
        QueryWrapper<TaskForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sno", taskForm.getSno());
        if (taskFormServiceimpl.update(taskForm, queryWrapper)) {
            return R.ok(200, "插入成功", taskForm);
        } else
            return R.fail(400, "fail", null);
    }

    @PutMapping("/lockornot/{Sno}/{lockit}")
    public R lockornot(@PathVariable int lockit, @PathVariable String Sno) {
        UpdateWrapper<Student> queryWrapper = new UpdateWrapper<>();
        queryWrapper.eq("Sno", Sno);
        queryWrapper.set("lock_task", lockit);
        if (studentService.update(null, queryWrapper)) {
            return R.ok(200, "插入成功", null);
        } else
            return R.fail(400, "fail", null);
    }

    @GetMapping("/showpdf")
    public void showpdf(HttpServletRequest request, HttpServletResponse response) throws
            OpenXML4JException, XmlException, IOException{
        String userName = request.getParameter("userName");
        R r = taskFormServiceimpl.createword(userName);
        ((XWPFDocument) r.getData()).write(new FileOutputStream("./temp.docx"));
        com.spire.doc.Document doc = new com.spire.doc.Document();
        doc.loadFromFile("./temp.docx");
        doc.saveToFile(response.getOutputStream(), FileFormat.PDF);
    }

    @GetMapping("/downloadword")
    public void downloadWord(HttpServletRequest request, HttpServletResponse response) throws IOException, OpenXML4JException, XmlException {
        String userName = request.getParameter("userName");
        R r = taskFormServiceimpl.createword(userName);
        ((XWPFDocument) r.getData()).write(new FileOutputStream("./temp.docx"));
        FileInputStream fileinputstream = new FileInputStream("./temp.docx");
        String docxfileName = r.getMsg();
        response.setContentType("application/msword;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(docxfileName, "utf-8"));
        com.spire.doc.Document doc = new com.spire.doc.Document();
        doc.loadFromFile("temp.docx");
        doc.saveToStream(response.getOutputStream(), FileFormat.Docx);
    }

    @GetMapping("/downloadpdf")
    public void word2pdf(HttpServletRequest request, HttpServletResponse response) throws IOException, OpenXML4JException, XmlException {
        String userName = request.getParameter("userName");
        R r = taskFormServiceimpl.createword(userName);
        ((XWPFDocument) r.getData()).write(new FileOutputStream("./temp.docx"));
        FileInputStream fileinputstream = new FileInputStream("./temp.docx");
        String docxfileName = r.getMsg();
        String pdffileName = docxfileName.substring(0, docxfileName.indexOf(".")) + ".pdf";
        response.setContentType("application/pdf;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(pdffileName, "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        com.spire.doc.Document doc = new com.spire.doc.Document();
        //加载Word
        doc.loadFromFile("temp.docx");
        //保存为PDF格式
        doc.saveToStream(outputStream, FileFormat.PDF);
    }


    @PostMapping("/vuepdf/{userName}")
    public void showvuepdf(@PathVariable String userName) throws
            OpenXML4JException, XmlException, IOException, InterruptedException {
        R r = taskFormServiceimpl.createword(userName);
        ((XWPFDocument) r.getData()).write(new FileOutputStream("./temp.docx"));
        com.spire.doc.Document doc = new com.spire.doc.Document();
        String docxfileName = r.getMsg();
        String pdffileName = docxfileName.substring(0, docxfileName.indexOf(".")) + ".pdf";
        doc.loadFromFile("./temp.docx");
        doc.saveToFile("./" + pdffileName);
        File file = new File("./" + pdffileName);
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inputStream.close();
        byte[] data = outStream.toByteArray();
        response.setContentType("application/pdf");
//        resp.setContentType(FileUtils.getContentType(FileUtils.getExtName(fileName)));
        // 设置返回文件名
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(pdffileName, "utf-8"));
        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
    }


//    public static String convertDocx2Pdf(String wordPath) {
//        OutputStream os = null;
//        InputStream is = null;
//        try {
//            is = new FileInputStream(new File(wordPath));
//            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(is);
//            Mapper fontMapper = new IdentityPlusMapper();
//            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
//            fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
//            fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
//            fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
//            fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
//            fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
//            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
//            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
//            fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
//            fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
//            fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
//            fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
//            fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
//            fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
//
//            mlPackage.setFontMapper(fontMapper);
//
//            //输出pdf文件路径和名称
//            String fileName = "pdfNoMark_" + System.currentTimeMillis() + ".pdf";
//            String pdfNoMarkPath = System.getProperty("java.io.tmpdir").replaceAll(separator + "$", "") + separator + fileName;
//            os = new java.io.FileOutputStream("d://temp.pdf");
//            //docx4j  docx转pdf
////            FOSettings foSettings = Docx4J.createFOSettings();
////            foSettings.setWmlPackage(mlPackage);
//            Docx4J.toPDF(mlPackage, os);
////            Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
//
//
//            is.close();//关闭输入流
//            os.close();//关闭输出流
//            //添加水印
////        return addTextMark(pdfNoMarkPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (os != null) {
//                    os.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            File file = new File(wordPath);
//            if (file != null && file.isFile() && file.exists()) {
//                file.delete();
//            }
//        }
//        return "";
//    }
//
//    public static void convertWordToPdf(String src, String desc) {
//        try {
//            //create file inputstream object to read data from file
//            FileInputStream fs = new FileInputStream(src);
//            //create document object to wrap the file inputstream object
//            XWPFDocument doc = new XWPFDocument(fs);
//            //72 units=1 inch
//            Document pdfdoc = new Document(PageSize.A4, 72, 72, 72, 72);
//            //create a pdf writer object to write text to mypdf.pdf file
//            PdfWriter pwriter = PdfWriter.getInstance(pdfdoc, new FileOutputStream(desc));
//            //specify the vertical space between the lines of text
//            pwriter.setInitialLeading(20);
//            //get all paragraphs from word docx
//            List<XWPFParagraph> plist = doc.getParagraphs();
//
//            //open pdf document for writing
//            pdfdoc.open();
//            for (int i = 0; i < plist.size(); i++) {
//                //read through the list of paragraphs
//                XWPFParagraph pa = plist.get(i);
//                //get all run objects from each paragraph
//                List<XWPFRun> runs = pa.getRuns();
//                //read through the run objects
//                for (int j = 0; j < runs.size(); j++) {
//                    XWPFRun run = runs.get(j);
//                    //get pictures from the run and add them to the pdf document
//                    List<XWPFPicture> piclist = run.getEmbeddedPictures();
//                    //traverse through the list and write each image to a file
//                    Iterator<XWPFPicture> iterator = piclist.iterator();
//                    while (iterator.hasNext()) {
//                        XWPFPicture pic = iterator.next();
//                        XWPFPictureData picdata = pic.getPictureData();
//                        byte[] bytepic = picdata.getData();
//                        Image imag = Image.getInstance(bytepic);
//                        pdfdoc.add(imag);
//
//                    }
//                    //get color code
//                    int color = getCode(run.getColor());
//                    //construct font object
//                    Font f = null;
//                    if (run.isBold() && run.isItalic())
//                        f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run.getFontSize(), Font.BOLDITALIC, new BaseColor(color));
//                    else if (run.isBold())
//                        f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run.getFontSize(), Font.BOLD, new BaseColor(color));
//                    else if (run.isItalic())
//                        f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run.getFontSize(), Font.ITALIC, new BaseColor(color));
//                    else if (run.isStrike())
//                        f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run.getFontSize(), Font.STRIKETHRU, new BaseColor(color));
//                    else
//                        f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run.getFontSize(), Font.NORMAL, new BaseColor(color));
//                    //construct unicode string
//                    String text = run.getText(-1);
//                    byte[] bs;
//                    if (text != null) {
//                        bs = text.getBytes();
//                        String str = new String(bs, "UTF-8");
//                        //add string to the pdf document
//                        Chunk chObj1 = new Chunk(str, f);
//                        pdfdoc.add(chObj1);
//                    }
//
//                }
//                //output new line
//                pdfdoc.add(new Chunk(Chunk.NEWLINE));
//            }
//            //close pdf document
//            pdfdoc.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static int getCode(String code) {
        int colorCode;
        if (code != null)
            colorCode = Long.decode("0x" + code).intValue();
        else
            colorCode = Long.decode("0x000000").intValue();
        return colorCode;
    }


}
