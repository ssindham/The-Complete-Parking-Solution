package com.nick.finalyearproject;

/**
 * Created by CompuCareInfotech on 3/22/2017.
 */

public class WebServiceClass {

    public static String URL="http://192.168.43.110/WebSite1/Service.asmx";
    //public static String URL="http://192.168.1.5/App_Code/WebSite1/Service.asmx";

}
/*


using System.Web.Services;
using System.Data.SqlClient;
using System.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Script.Serialization;
using System.Net;
using System.Text;
using System.IO;


[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line.
// [System.Web.Script.Services.ScriptService]

public class Service : System.Web.Services.WebService
{
    SqlConnection cn = new SqlConnection("Data Source=.\\SQL2008;Initial Catalog=SmartPark;Persist Security Info=True;User ID=sa;Password=codesquad");
    public Service()
    {

        //Uncomment the following line if using designed components
        //InitializeComponent();
    }

    [WebMethod]
    public string HelloWorld()
    {
        return "Hello World";
    }



    [WebMethod]
    public string Reg(String name, String email, String mob, String pass, String profession, String secretcode)
    {
        cn.Open();
        if (profession.Equals("Citizen"))
        {
            SqlCommand cmd = new SqlCommand("insert into user_table(name,email,mob,pass,profession,secretcode,veh_no,pending_fine,pending_charge) values('" + name + "','" + email + "','" + mob + "','" + pass + "','" + profession + "','Dummy', '" + secretcode + "', 0,0)", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();

            if (row > 0)
            {
                return "reg successfully";
            }
            else
            {
                return "error";
            }
        }

        //to check police or attendant
        if (profession.Equals("Police"))
        {
            SqlDataAdapter sda = new SqlDataAdapter("select * from police_table where p_name = '" + name + "' and secretcode ='" + secretcode + "'", cn);
            DataTable dt = new DataTable();
            //return "select * from police_table where p_name = '" + name + "' and secretcode ='" + secretcode + "'";
            sda.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                if (dt.Rows[0][1].ToString().Equals(name) && dt.Rows[0][2].ToString().Equals(secretcode))
                {
                    //  return "valid";
                    SqlCommand cmd = new SqlCommand("insert into user_table(name,email,mob,pass,profession,secretcode,veh_no,pending_fine,pending_charge) values('" + name + "','" + email + "','" + mob + "','" + pass + "','" + profession + "','" + secretcode + "', 'Dummy' , 0,0)", cn);
                    int row = cmd.ExecuteNonQuery();
                    cn.Close();

                    if (row > 0)
                    {
                        return "reg successfully";
                    }
                    else
                    {
                        return "error";
                    }
                }
                else
                {
                    return "error";
                }
            }

            else
            {
                return "Invalid Police Code";
            }
        }

        if (profession.Equals("Attendant"))
        {
            SqlDataAdapter sda = new SqlDataAdapter("select * from attendant_table where a_name = '" + name + "' and secretcode ='" + secretcode + "'", cn);
            DataTable dt = new DataTable();

            sda.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                if (dt.Rows[0][1].ToString().Equals(name) && dt.Rows[0][2].ToString().Equals(secretcode))
                {
                    //  return "valid";
                    SqlCommand cmd = new SqlCommand("insert into user_table(name,email,mob,pass,profession,secretcode,veh_no,pending_fine,pending_charge) values('" + name + "','" + email + "','" + mob + "','" + pass + "','" + profession + "','" + secretcode + "', 'Dummy' , 0,0)", cn);
                    int row = cmd.ExecuteNonQuery();
                    cn.Close();

                    if (row > 0)
                    {
                        return "reg successfully";
                    }
                    else
                    {
                        return "error";
                    }
                }
                else
                {
                    return "Invalid Attendant Code";
                }
            }

            else
            {
                return "invalid";
            }
        }

        return "invalid";
    }





    [WebMethod]
    public string login(String email, String pass)
    {
        cn.Open();
        String result = null;
        SqlDataAdapter sda = new SqlDataAdapter("select * from user_table where email = '" + email + "' and pass ='" + pass + "'", cn);
        DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            if (dt.Rows[0][2].ToString().Equals(email) && dt.Rows[0][4].ToString().Equals(pass))
            {
                //    return "valid";
                result = dt.Rows[0][0].ToString() + "," + dt.Rows[0][5].ToString();
                return result;
            }
            else
            {
                return "invalid";
            }
        }
        else
        {
            return "invalid";
        }
    }



    [WebMethod]
    public string RentPlace(String email, String longi, String lati, String two_wheel, String four_wheel, String l_name, String alloted, String week_days, String start_time, String end_time, String charge_per_hour)
    {
        cn.Open();
        String u_id = "dummy";
        if (two_wheel == null)
            two_wheel = "0";
        if (four_wheel == null)
            four_wheel = "0";
        SqlDataAdapter sda = new SqlDataAdapter("select * from user_table where email = '" + email + "' ", cn);
        DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            u_id = dt.Rows[0][0].ToString();
        }
        SqlCommand cmd = new SqlCommand("insert into rentplace_table(u_id,longi,lati,two_wheel,four_wheel,l_name,alloted,week_days,start_time,end_time,charge_per_hour) values('" + u_id + "','" + longi + "','" + lati + "','" + two_wheel + "','" + four_wheel + "','" + l_name + "', 'No' ,'" + week_days + "','" + start_time + "','" + end_time + "','" + charge_per_hour + "')", cn);
        int row = cmd.ExecuteNonQuery();
        cn.Close();

        if (row > 0)
        {
            return "rent place registered successfully";
        }
        else
        {
            return "error";
        }
    }

    [WebMethod]
    public string ReturnRentPlaces(String two_wheel, String four_wheel, String today_name)
    {
        int two = Convert.ToInt32(two_wheel);
        int four = Convert.ToInt32(four_wheel);
        cn.Open();
        String result = null;
        String l_name = "dummy", dist = "dummy", charge_per_hour = "dummy", u_id = "dummy", r_id = "dummy", longi = "dummy", lati = "dummy", week_days = "dummy";


        SqlCommand cmd = new SqlCommand("select * from rentplace_table where two_wheel >= '" + two + "' AND four_wheel >= '" + four + "'");
        cmd.Connection = cn;
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            l_name = (string)reader["l_name"];
            dist = (string)reader["start_time"];
            charge_per_hour = (string)reader["charge_per_hour"].ToString();
            u_id = (string)reader["u_id"].ToString();
            r_id = (string)reader["r_id"].ToString();
            lati = (string)reader["lati"];
            longi = (string)reader["longi"];
            week_days = (string)reader["week_days"];
            if (week_days.Contains(today_name.Substring(0, 2)))
            {
                result += (l_name + "~" + dist + "~" + charge_per_hour + "~" + u_id + "~" + r_id + "~" + lati + "~" + longi + "~" + week_days + "#");
            }
        }

        //  result = l_name + dist + charge_per_hour;
        cn.Close();
        if (result == null)
        {
            return "no place found";
        }
        return result;


    }




    [WebMethod]
    public string ReturnTenantInfo(String u_id, String r_id)
    {
        int u_id1 = Convert.ToInt32(u_id);
        int r_id1 = Convert.ToInt32(r_id);
        String result = null;
        cn.Open();
        String t_name = "dummy", mob = "dummy", l_name = "dummy", charge = "dummy", dist = "dummy";


        SqlCommand cmd = new SqlCommand("select * from user_table where u_id='" + u_id1 + "'");
        cmd.Connection = cn;
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            t_name = (string)reader["name"];
            mob = (string)reader["mob"];

            result += (t_name + "~" + mob + "~");
        }
        reader.Close();
        cmd = new SqlCommand("select * from rentplace_table where r_id='" + r_id1 + "'");
        cmd.Connection = cn;
        reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            l_name = (string)reader["l_name"];
            charge = (string)reader["charge_per_hour"].ToString();
            dist = "45";

            result += (l_name + "~" + charge + "~" + dist);
        }

        //  result = l_name + dist + charge_per_hour;
        cn.Close();
        return result;

    }

    [WebMethod]
    public string ReturnAllRentPlacesOfAUser(String u_id)
    {
        int u_id1 = Convert.ToInt32(u_id);
        String result = null;
        cn.Open();
        String r_id = "dummy", longi = "dummy", lati = "dummy", two_wheel = "dummy", four_wheel = "dummy", l_name = "dummy", week_days = "dummy", start_time = "dummy", end_time = "dummy", charge_per_hour = "dummy";


        SqlCommand cmd = new SqlCommand("select * from rentplace_table where u_id='" + u_id1 + "'");
        cmd.Connection = cn;
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {

            longi = (string)reader["longi"];
            lati = (string)reader["lati"];
            two_wheel = (string)reader["two_wheel"].ToString();
            four_wheel = (string)reader["four_wheel"].ToString();
            l_name = (string)reader["l_name"];
            week_days = (string)reader["week_days"];
            start_time = (string)reader["start_time"];
            end_time = (string)reader["end_time"];
            charge_per_hour = (string)reader["charge_per_hour"].ToString();
            r_id = (string)reader["r_id"].ToString();

            result += (lati + "~" + longi + "~" + two_wheel + "~" + four_wheel + "~" + l_name + "~" + week_days + "~" + start_time + "~" + end_time + "~" + charge_per_hour + "~" + r_id + "#");
        }

        //  result = l_name + dist + charge_per_hour;
        cn.Close();
        if (result == null)
        {
            return "No Place Registered";
        }
        return result;

    }


    [WebMethod]
    public string RegisterPushId(String u_id, String push_id)
    {
        int u_id1 = Convert.ToInt32(u_id);


        cn.Open();
        //SqlDataAdapter sda = new SqlDataAdapter("select * from push_table where u_id = '" + u_id "' ", cn);
        SqlDataAdapter sda = new SqlDataAdapter("select * from push_table where u_id = '" + u_id + "' ", cn);
        DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            //return "The Push Id for this User Id has already been pushed";
            SqlCommand cmd = new SqlCommand("update push_table set push_id= '" + push_id + "' where u_id= '" + u_id1 + "' ", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();

            if (row > 0)
            {
                return "Updated successfully";
            }
            else
            {
                return "error in update";
            }
        }
        else
        {

            SqlCommand cmd = new SqlCommand("insert into push_table(u_id,push_id) values('" + u_id1 + "','" + push_id + "')", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();

            if (row > 0)
            {
                return "Pushed successfully";
            }
            else
            {
                return "error in push";
            }
        }
    }

    [WebMethod]
    public string deleteorUpdateRentPlace(String r_id, String longi, String lati, String two_wheel, String four_wheel, String l_name, String week_days, String start_time, String end_time, String charge_per_hour, String opcode)
    {
        cn.Open();
        int r_id1 = Convert.ToInt32(r_id);
        if (opcode.Equals("0"))
        {
            SqlCommand cmd = new SqlCommand("delete from rentplace_table where r_id='" + r_id1 + "'", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();

            if (row > 0)
            {
                return "Deleted Succesfully";
            }
            else
            {
                return "error in delete";
            }
        }
        else
        {
            int two_wheel1 = Convert.ToInt32(two_wheel);
            int four_wheel1 = Convert.ToInt32(four_wheel);
            int charge_per_hour1 = Convert.ToInt32(charge_per_hour);
            SqlCommand cmd = new SqlCommand("update rentplace_table set longi= '" + longi + "'  , lati = '" + lati + "'  , two_wheel = '" + two_wheel1 + "' , four_wheel= '" + four_wheel1 + "' ,l_name= '" + l_name + "' ,week_days = '" + week_days + "' ,start_time='" + start_time + "' ,end_time='" + end_time + "' ,charge_per_hour= '" + charge_per_hour1 + "' where r_id= '" + r_id1 + "' ", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();
            if (row > 0)
            {
                return "Updated Succesfully";
            }
            else
            {
                return "error in update";
            }
        }

    }

    [WebMethod]
    public string getPushIdbyVehNoAndSendNotification(String veh_no, String fine, String police_id)
    {
        int fine1 = Convert.ToInt32(fine);
        cn.Open();
        SqlDataAdapter sda = new SqlDataAdapter("select push_table.push_id  from user_table,push_table where user_table.veh_no = '" + veh_no + "' and user_table.u_id=push_table.u_id", cn);
        DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {

            List<string> list = new List<string>();
            //   list.Add("APA91bHrPFukaln5pRTmCUX1GNwHjen1lwu7FSDtF3SyEW0aTDsABzcv7rsatsq5ytMXSxBfkYssOd6i2lIrBEj3t_zSa3CMbXGfi5tN0lO3cecIxKfDhngKqWTL7-nypDIWkpcpGw2I");
            list.Add(dt.Rows[0][0].ToString());
          //  list.Add("APA91bGLkopXB9YqdG_jhkwlJtIqWZ3ufBKwzsltYDptglBWh-1ql91SAijfoevl-L1AVSIn0bVowdRrnGppZjIsL4aCCSVAsiR7udo7ut6Go1bW9KyCll_OoNHO4OmTnKpJz7eE84Zr");

            //Response.Write(SendNotification(list, "hello", "hi", 1));
            SendNotification(list, "Rs." + fine + " Fine Delay From Police Id:" + police_id, "hi", 1);
            //return "Notification Sent";
            SqlCommand cmd = new SqlCommand("update user_table set pending_fine= pending_fine+ '" + fine1 + "' where veh_no= '" + veh_no + "' ", cn);
            int row = cmd.ExecuteNonQuery();
            cn.Close();
            return police_id;


        }
        else
        {
            return "error";
        }
    }

    public string SendNotification(List<string> deviceRegIds, string message, string title, long id)
    {

        string SERVER_API_KEY = "AIzaSyBythzwTDsPkD-DHOx9Fn20lJENxEAWbMM";
        //	string SERVER_API_KEY = "";
        string SENDER_ID = "1034075539594";
        string regIds = string.Join("\",\"", deviceRegIds.ToArray());

        NotificationMessage nm = new NotificationMessage();

        nm.Message = message;


        var value = new JavaScriptSerializer().Serialize(nm);
        //     Response.Write(value);

        WebRequest tRequest;
        tRequest = WebRequest.Create("https://android.googleapis.com/gcm/send");
        tRequest.Method = "post";
        tRequest.ContentType = "application/json";
        tRequest.Headers.Add(string.Format("Authorization: key={0}", SERVER_API_KEY));

        tRequest.Headers.Add(string.Format("Sender: id={0}", SENDER_ID));

        string postData = "{\"collapse_key\":\"score_update\",\"time_to_live\":108,\"delay_while_idle\":true,\"data\": { \"message\" : " + value + ",\"time\": " + "\"" + System.DateTime.Now.ToString() + "\"},\"registration_ids\":[\"" + regIds + "\"]}";


        Byte[] byteArray = Encoding.UTF8.GetBytes(postData);
        tRequest.ContentLength = byteArray.Length;

        Stream dataStream = tRequest.GetRequestStream();
        dataStream.Write(byteArray, 0, byteArray.Length);
        dataStream.Close();

        WebResponse tResponse = tRequest.GetResponse();

        dataStream = tResponse.GetResponseStream();

        StreamReader tReader = new StreamReader(dataStream);

        String sResponseFromServer = tReader.ReadToEnd();

        tReader.Close();
        dataStream.Close();
        tResponse.Close();
        return sResponseFromServer;
    }

    [WebMethod]
    public string getgovtparkingplaces()
    {
        String result = null;
        cn.Open();
        String longi = "dummy", lati = "dummy";


        SqlCommand cmd = new SqlCommand("select * from govt_rent_places_table");
        cmd.Connection = cn;
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {

            longi = (string)reader["long"];
            lati = (string)reader["lat"];
            result += (lati + "~" + longi + "#");
        }

        //  result = l_name + dist + charge_per_hour;
        cn.Close();
        if (result == null)
        {
            return "No Govt Rent Places Found";
        }
        return result;

    }
    [WebMethod]
    public string updatepolicecollectionandsendnotification(String penalty_user_id,String police_id, String amnt)
    {

        int police_id1 = Convert.ToInt32(police_id);
        int amnt1 = Convert.ToInt32(amnt);


        cn.Open();
        //SqlDataAdapter sda = new SqlDataAdapter("select * from push_table where u_id = '" + u_id "' ", cn);
        SqlDataAdapter sda = new SqlDataAdapter("select secretcode from user_table where u_id = '" + police_id1 + "' ", cn);
        DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            //return "The Push Id for this User Id has already been pushed";
            SqlCommand cmd = new SqlCommand("update police_table set collected_fine= collected_fine+ '" + amnt1 + "' where secretcode= '" +dt.Rows[0][0].ToString() + "' ", cn);
            int row = cmd.ExecuteNonQuery();


            if (row > 0)
            {
                SqlDataAdapter sda1 = new SqlDataAdapter("select push_id  from push_table where u_id= '" + police_id1 + "' ", cn);
                DataTable dt1 = new DataTable();
                sda.Fill(dt1);
                List<string> list = new List<string>();
                list.Add(dt1.Rows[0][0].ToString());
                  list.Add("APA91bGeaKHcSQuZYcU71mfPH8U5bEftSSsJ1JbjE9nIHtSP7vrbI9h6QlX0Z6wBKQkZo2PRj_ldHyrja6Gz9HXFT-tpxC-EpQvuh3B3UuTlyzjSik6wPbm5Mt96xtth9hHKcEeu6g2L");
                SendNotification(list, "Rs." + amnt1 + " Paid By User Id:" + penalty_user_id, "hi", 1);

               cmd = new SqlCommand("update user_table set pending_fine= pending_fine-'"+  amnt1 + "' where u_id= '" + penalty_user_id + "' ", cn);
                row = cmd.ExecuteNonQuery();
                cn.Close();
                return "Police Collection Updated Successfully";

            }
            else
            {
                return "error in update";
            }
        }
        else
        {
            return "error in update";
        }

    }

    [WebMethod]
    public string getcollectionamount(String police_id)
    {
        cn.Open();
        int police_id1 = Convert.ToInt32(police_id);
        SqlDataAdapter sda = new SqlDataAdapter("select secretcode from user_table where u_id = '" + police_id + "' ", cn);
         DataTable dt = new DataTable();
        sda.Fill(dt);
        if (dt.Rows.Count > 0)
        {

            SqlDataAdapter sda1 = new SqlDataAdapter("select collected_fine  from police_table where secretcode= '" + dt.Rows[0][0].ToString() + "' ", cn);
            DataTable dt1 = new DataTable();
            sda1.Fill(dt1);
            return dt1.Rows[0][0].ToString();
        }
        else
        {
            return "error";
        }

    }


    [WebMethod]
    public string getDataFromParkingPlaces()
    {

        String result = null;
        cn.Open();
        String sr_no = "dummy", longi = "dummy", lati = "dummy", l_name = "dummy", attendant_name = "dummy", current_capacity = "dummy", capacity="dummy";

        SqlCommand cmd = new SqlCommand("select * from govt_rent_places_table");
        cmd.Connection = cn;
        SqlDataReader reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            sr_no = (string)reader["sr_no"].ToString();
            longi = (string)reader["long"];
            lati = (string)reader["lat"];
            l_name = (string)reader["l_name"];
            capacity = (string)reader["capacity"].ToString();
            current_capacity = (string)reader["current_capacity"].ToString();
            attendant_name = (string)reader["attendant_name"];
            result += (sr_no+"~"+lati + "~" + longi + "~" + l_name + "~" +capacity+"~"+current_capacity+"~"+ attendant_name + "#");
        }
        cn.Close();
        if (result == null)
        {
            return "No Govt Rent Places Found";
        }
        return result;
       }


}
 */