package stdio.kiteDream.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.user.bean.User;

public class CreateXL {

	/** Excel 文件要存放的位置 */

	public static File createUserExcel(List<User> users) {
		String outputFile = new Date().getTime() + "";
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Nickname");
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Age");
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Register time");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Init location");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("green coin");
			cell = row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("yellow coin");
			cell = row.createCell(6);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("red coin");
			cell = row.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("level");
			cell = row.createCell(8);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("score");
			cell = row.createCell(9);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("logins");
			cell = row.createCell(10);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("total time");
			cell = row.createCell(11);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group type");
			cell = row.createCell(12);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group org");
			cell = row.createCell(13);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group name");
			cell = row.createCell(14);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("group creater");
			int rowIndex = 1;
			for (User user : users) {
				try {
					row = sheet.createRow(rowIndex++);
					cell = row.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getNickname());
					cell = row.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					try {
						cell.setCellValue(year - Integer.parseInt(user.getBirthday()));
					} catch (Exception e) {
						cell.setCellValue("");
					}
					cell = row.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCreate_time() == null ? "" : Constant.TIME.format(user.getCreate_time()));
					cell = row.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getLatitude() + "," + user.getLongitude());
					cell = row.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins() == null ? 0 : user.getCoins().getGreenNum());
					cell = row.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins() == null ? 0 : user.getCoins().getYellowNum());
					cell = row.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getCoins() == null ? 0 : user.getCoins().getRedNum());
					cell = row.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getHigh_level());
					cell = row.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getHigh_level_stage() + "/" + user.getHigh_level_all());
					cell = row.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("logins");
					cell = row.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("total time");
					cell = row.createCell(11);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup() == null ? "" : user.getGroup().getCategory().getName());
					cell = row.createCell(12);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup() == null ? "" : user.getGroup().getGroupOrg().getName());
					cell = row.createCell(13);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup() == null ? "" : user.getGroup().getName());
					cell = row.createCell(14);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getGroup() == null ? false : user.getGroup().getCreatername().equals(user.getNickname()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(outputFile);
	}

	public static File createImageExcel(List<Image> images) {
		String outputFile = new Date().getTime() + "";
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Url");
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Level");
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Task");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Captions");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Submit Time");
			cell = row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("GPS");
			cell = row.createCell(6);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("User Name");
			cell = row.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Status");
			cell = row.createCell(8);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Update Time");
			int rowIndex = 1;
			for (Image image : images) {
				try {
					row = sheet.createRow(rowIndex++);
					cell = row.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(BasePathJsonParser.basePath+image.getPath());
					cell = row.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(image.getLevel());
					cell = row.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(image.getLevel_stage());
					cell = row.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(image.getName()==null?"":image.getName());
					cell = row.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(Constant.TIME.format(image.getCreate_time()));
					cell = row.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue((image.getLatitude()==null?"":image.getLatitude()) + "," + (image.getLongitude()==null?"":image.getLongitude()));
					cell = row.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(image.getUser().getNickname());
					
					String statu="Street Mode";
					if(Image.Check.PASS.equals(image.getStatu())){
						statu = "Pass";
					}else if(Image.Check.FAIL.equals(image.getStatu())){
						statu = "Deny";
					}else if(Image.Check.UNREAD.equals(image.getStatu())){
						statu = "Unread";
					}
					if(Image.Type.STREET.equals(image.getLevelType())){
						statu = "Street Mode";
					}
					cell = row.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(statu);
					cell = row.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(Constant.TIME.format(image.getUpdate_time()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fOut = new FileOutputStream(outputFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(outputFile);
	}
}