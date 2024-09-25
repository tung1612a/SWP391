/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
public class PhoneNumberValidator {
      // Phương thức kiểm tra số điện thoại hợp lệ
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Số điện thoại bắt đầu bằng 0 và phải có đúng 10 số
        String regex = "^0\\d{9}$";
        return phoneNumber.matches(regex);
    }

    public static void main(String[] args) {
        String phoneNumber = "0123456789";
        if (isValidPhoneNumber(phoneNumber)) {
            System.out.println("Số điện thoại hợp lệ");
        } else {
            System.out.println("Số điện thoại không hợp lệ");
        }
    }
}
