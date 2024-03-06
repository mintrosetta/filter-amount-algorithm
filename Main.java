import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> remarks = new ArrayList<>();
        remarks.add("รบกวนยกเลิกหักคอมฝ่ายขาขยอด 2085.02 บาทค่ะ");
        remarks.add("รฝ/ข 1.07บ.ขอบคุณครับ");
        remarks.add("ลูกค้าชำระเงินผ่านบัตรเครดิตกสิกรไทยวันที่ 27/4/2020 ยอด7084.47 บาท เช็คบัญชีเงินเกิม เบอร์ 5216 แจ้งมียอดเงินเกินแล้วครับ");
        remarks.add("รบกวนยกเลิกหักคอมฝ่ายขายยอด 2417.13 บาทด้วยค่ะ ฝ่ายขายตัดยอดแล้วค่ะ วันที่ 08/05/2563 ขอบคุณค่ะ");
        remarks.add("ยกเลิกการหักคอม ฝ/ข ค่ะ ล/ค โอนเงินเข้ามาแล้ว 2,000 บาท ตั้งแต่วันที่ 11/5 ค่ะ ฝ/ข ส่งหลักฐานให้ทางบัญชีแล้ว รบกวนตัดยอดด้วยค่ะ");
        remarks.add("ยกเลิกการหักคอม ฝ/ข ค่ะ ล/ค โอนเงินเข้ามาแล้ว 2,000...4 บาท ตั้งแต่วันที่ 11/5 ค่ะ ฝ/ข ส่งหลักฐานให้ทางบัญชีแล้ว รบกวนตัดยอดด้วยค่ะ");
        remarks.add("หักคอมฝ่ายขาย .85");
        remarks.add("ติดต่อเบอร์ 0800710622 หักคอมฝ่ายขาย 102.98 ย้อนหลัง ณ วันที่ 27/4/2020");
        remarks.add("ส.12939 รวม 13900");

        System.out.println();
        for (String remark : remarks) {
            boolean isActivateCollectChar = false;
            StringBuilder amountBuilder = new StringBuilder();

            String lastAmount = "";
            int lastScore = -1;

            for (int remarkIndex = 0; remarkIndex < remark.length(); remarkIndex++) {
                char character = remark.charAt(remarkIndex);

                if (isNumber(character)) {
                    isActivateCollectChar = true;
                }

                if (isDot(character)) {
                    char nextCharacter = remark.charAt(remarkIndex + 1);
                    if ((remarkIndex + 1) < remark.length() && isNumber(nextCharacter)) {
                        isActivateCollectChar = true;
                    }
                }

                if (isWhiteSpace(character) || isThaiCharacter(character) || remarkIndex == remark.length() - 1) {
                    if (remarkIndex == remark.length() - 1 && amountBuilder.length() > 0)
                        amountBuilder.append(character);

                    isActivateCollectChar = false;

                    if (amountBuilder.length() > 0) {
                        String rawAmount = amountBuilder.toString();
                        int score = 0;

                        for (int rawAmountIndex = 0; rawAmountIndex < rawAmount.length(); rawAmountIndex++) {
                            char rawAmountCharacter = rawAmount.charAt(rawAmountIndex);

                            if (!isNumber(rawAmountCharacter) && !isDot(rawAmountCharacter) && !isComma(rawAmountCharacter)) {
                                score -= 1;
                            }

                            if (isDot(rawAmountCharacter)) {
                                if (rawAmountIndex < rawAmount.length() - 1 && isNumber(rawAmount.charAt(rawAmountIndex + 1))) {
                                    score += 1;
                                }
                            }

                            if (isNumber(rawAmountCharacter)) {
                                if (rawAmountCharacter == '0' && rawAmountIndex == 0 && rawAmount.charAt(rawAmountIndex + 1) != '.') {
                                    score -= 10;
                                }
                            }
                                
                        }

                        if (score >= lastScore) {
                            lastAmount = rawAmount;
                            lastScore = score;
                        }

                        amountBuilder.setLength(0);
                    }
                }

                if (isActivateCollectChar) {
                    amountBuilder.append(character);
                }
            }

            System.out.println("จากข้อมูล: " + remark);
            System.out.println("สามารถถอดจำนวนเงินได้: " + convertToDouble(lastAmount) + "\n");
        }
    }

    public static boolean isNumber(char character) {
        return (character >= '0' && character <= '9');
    }

    public static boolean isDot(char character) {
        return (character == '.');
    }

    public static boolean isWhiteSpace(char character) {
        return (character == ' ');
    }

    public static boolean isThaiCharacter(char character) {
        return (character >= '\u0E01' && character <= '\u0E5B');
    }

    public static boolean isComma(char character) {
        return (character == ',');
    }

    public static boolean isPhoneNumber(String text) {
        return false;
    }

    public static double convertToDouble(String text) {
        try {
            StringBuffer textNumber = new StringBuffer();
            boolean isDuplicateDot = false;

            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);

                if (isNumber(character)) {
                    textNumber.append(character);
                    continue;
                }

                if (isDot(character) && !isDuplicateDot) {
                    textNumber.append(character);
                    isDuplicateDot = true;
                }
            }

            return Double.parseDouble(textNumber.toString());
        } catch (Exception ex) {
            return 0;
        }
    }
}