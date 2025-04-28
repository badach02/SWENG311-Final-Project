import javax.swing.*;
import java.awt.*;
import java.util.List;

class ReceiptManagerPanel extends JPanel {
    private DefaultListModel<Receipt> receiptListModel;
    private JList<Receipt> receiptJList;
    private List<Receipt> receipts;

    public ReceiptManagerPanel(List<Receipt> receipts) {
        this.receipts = receipts;
        receiptListModel = new DefaultListModel<>();
        
        for (Receipt r : receipts) {
            receiptListModel.addElement(r);
        }

        receiptJList = new JList<>(receiptListModel);
        JScrollPane scrollPane = new JScrollPane(receiptJList);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addReceipt(Receipt r) {
        receipts.add(r);
        receiptListModel.addElement(r);
    }
    
    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void updateReceiptsList() {
        receiptListModel.clear();
        for (Receipt r : receipts) {
            receiptListModel.addElement(r);
        }
    }
}
