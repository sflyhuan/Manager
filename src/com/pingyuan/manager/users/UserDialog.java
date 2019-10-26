package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.User;

import javax.swing.*;

public class UserDialog extends JDialog {

   private User mUser;

    public UserDialog(User mUser) {
        this.mUser = mUser;
    }

    public UserDialog() {
        this.setLayout(null);




        if (mUser==null){
            //add
        }else {
            //edit
        }

    }
}
