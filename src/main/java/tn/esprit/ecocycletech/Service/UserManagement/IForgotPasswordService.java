package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.DTO.MailBody;

public interface IForgotPasswordService {
    void sendSimpleMessage(MailBody mailBody);

}
