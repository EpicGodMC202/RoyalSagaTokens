package me.epicgodmc.royalsagatokens.Objects;

import java.util.UUID;

public class TokenPlayer
{
    UUID uuid;
    int tokens;
    String currentShop;

    public TokenPlayer(UUID uuid, int tokens){
        this.uuid = uuid;
        this.tokens = tokens;
    }

    public boolean isInShop()
    {
        if (currentShop == null || currentShop.equalsIgnoreCase("null")){
            return false;
        }
        if (currentShop.equalsIgnoreCase("main"))
        {
            return false;
        }
        return true;
    }

    public String getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(String currentShop) {
        this.currentShop = currentShop;
    }

    public void payTokens(TokenPlayer tokenPlayer, int tokens)
    {
        this.tokens -= tokens;
        tokenPlayer.addTokens(tokens);
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void removeTokens(int tokens)
    {
        this.tokens -= tokens;
    }
    public void addTokens(int tokens)
    {
        this.tokens += tokens;
    }

    public boolean hasFunds(int amt)
    {
        return tokens >= amt;
    }
}
