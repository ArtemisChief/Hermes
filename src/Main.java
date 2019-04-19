import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import view.MailConnectionView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            System.setProperty("sun.java2d.noddraw", "true");
            UIManager.put("RootPane.setupButtonVisible", false);
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MailConnectionView mailConnectionView = new MailConnectionView();
        mailConnectionView.setVisible(true);
    }

}