/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import context.DBContext;
import entity.Digital;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Digital getTop1() {
        String query = "select top 1 *from digital\n"
                + "order by timePost desc";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Digital(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7));
            }
        } catch (Exception e) {
        }

        return null;

    }

    public Digital getDetail(String id) {
        String query = "select *from digital\n"
                + "where id=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Digital(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7));
            }
        } catch (Exception e) {
        }

        return null;

    }

    public List<Digital> getTop5() {
        List<Digital> list = new ArrayList<>();
        String query = "select top 5 * from digital\n"
                + "order by timePost desc";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Digital(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Digital> search(String txt, int index, int size) {
        List<Digital> list = new ArrayList<>();
        String query = "with result as(select *,ROW_NUMBER() over (order by id) as r\n"
                + " from digital\n"
                + "where title like ?)\n"
                + "select * from result\n"
                + "where r between ? and ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txt + "%");
            ps.setInt(2, index * size - (size - 1));
            ps.setInt(3, index * size);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Digital(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int countResult(String txt) {
        String query = "select count(*) from digital\n"
                + "where title like  ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txt + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

//    public String hightLight(String title, String txt) {
//        String replaceText = "<span class=\"highLight\">" + txt + "</span>";
//        title = title.replaceAll(txt, replaceText);
//        return title;
//    }
//
//    public static void main(String[] args) {
//        DAO dao = new DAO();
//        Digital d = dao.getTop1();
//        System.out.println(d);
//        int count = dao.countResult("d");
//        System.out.println(count);
//
//    }
}
