/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;
import trangcq.role.RoleDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RegistrationDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public RegistrationDAO() {
    }

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
     public RegistrationDTO checkLogin(String username, String password) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "Select Re.Id, Username, Re.[Name], [Password], R.Id AS IdRole, R.Name AS RoleName " +
                        " From Registration Re JOIN Role R ON R.Id=Re.RoleId Where Username = ? And Password = ? AND StatusId = 1";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                int roleID = rs.getInt("IdRole");
                String roleName = rs.getString("RoleName");
                result = new RegistrationDTO(id, username, password, name, new StatusDTO(1), new RoleDTO(roleID, roleName));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
