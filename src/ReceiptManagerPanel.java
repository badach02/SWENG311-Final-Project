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
        
        // Add existing receipts to the list model
        for (Receipt r : receipts) {
            receiptListModel.addElement(r);
        }

        receiptJList = new JList<>(receiptListModel);
        JScrollPane scrollPane = new JScrollPane(receiptJList);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Add a new receipt to the list
    public void addReceipt(Receipt r) {
        receipts.add(r);
        receiptListModel.addElement(r);
    }
    
    // Get the list of receipts
    public List<Receipt> getReceipts() {
        return receipts;
    }

    // Method to update the receipt list (if needed)
    public void updateReceiptsList() {
        receiptListModel.clear();
        for (Receipt r : receipts) {
            receiptListModel.addElement(r);
        }
    }
}
