package me.epicgodmc.royalsagatokens.utilities;

public class MsgFormatter {
    private String text;

    public MsgFormatter(String str) {
        this.text = str;
    }


    public MsgFormatter formatDesc(String desc) {
        this.text = this.text.replace("%desc%", desc).replace("%DESC%", desc);
        return this;
    }

    public MsgFormatter formatName(String name) {
        this.text = this.text.replace("%name%", name).replace("%NAME%", name);
        return this;
    }

    public MsgFormatter formatNumber(int number) {
        this.text = this.text.replace("%number%", String.valueOf(number)).replace("%NUMBER%", String.valueOf(number));
        return this;
    }

    public MsgFormatter formatItem(String itemName) {
        this.text = this.text.replace("%item%", itemName).replace("%ITEM%", itemName);
        return this;
    }

    public MsgFormatter formatPlayer(String playerName) {
        this.text = this.text.replace("%PLAYER%", playerName).replace("%player%", playerName);
        return this;
    }

    public MsgFormatter formatAmount(int amount) {
        this.text = this.text.replace("%amount%", String.valueOf(amount)).replace("%AMOUNT%", String.valueOf(amount));
        return this;
    }

    public MsgFormatter formatCost(int amount) {
        this.text = this.text.replace("%cost%", String.valueOf(amount)).replace("%COST%", String.valueOf(amount));
        return this;
    }

    public MsgFormatter formatBalance(int amount) {
        this.text = this.text.replace("%tokens%", String.valueOf(amount)).replace("%TOKENS%", String.valueOf(amount));
        return this;
    }

    @Override
    public String toString() {
        return text;
    }

}
