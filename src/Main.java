import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import view.MailSocketView;
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

        MailSocketView mailSocketView = new MailSocketView();
        mailSocketView.setVisible(true);
    }
}