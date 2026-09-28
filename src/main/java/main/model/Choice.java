package main.model;

import static main.model.Flag.FLAG_NONE;

public class Choice {

    public static final Choice NONE = new Choice(
            "Sem texto", "Sem alvo", 0, 0, 0);

    private final String text;
    private final String nameTargetNpc;
    private final int sanityChange;
    private final int knowledgeChange;
    private final int trustLevelChange;
    private String requireItemId = Item.NONE.getId();
    private Item rewardItem = Item.NONE;
    private String requireFlag = FLAG_NONE;
    private String generatedFlag = FLAG_NONE;

    public Choice(String text, String nameTargetNpc, int sanityChange, int knowledgeChange, int trustLevelChange,
                  String idRequireItem, Item rewardItem, String requireFlag, String generatedFlag) {
        this(text, nameTargetNpc, sanityChange, knowledgeChange, trustLevelChange);
        this.requireItemId = idRequireItem;
        this.rewardItem = rewardItem;
        this.requireFlag = requireFlag;
        this.generatedFlag = generatedFlag;
    }

    public Choice(String text, String nameTargetNpc, int sanityChange, int knowledgeChange, int trustLevelChange) {
        this.text = text;
        this.nameTargetNpc = nameTargetNpc;
        this.sanityChange = sanityChange;
        this.knowledgeChange = knowledgeChange;
        this.trustLevelChange = trustLevelChange;
    }

    public Choice(String text) {
        this.text = text;
        this.nameTargetNpc = Npc.NONE.getName();
        this.sanityChange = 0;
        this.knowledgeChange = 0;
        this.trustLevelChange = 0;
    }

    public Choice(String text, String nameTargetNpc, int trustLevelChange) {
        this.text = text;
        this.nameTargetNpc = nameTargetNpc;
        this.sanityChange = 0;
        this.knowledgeChange = 0;
        this.trustLevelChange = trustLevelChange;
    }

    public boolean hasRewardItem() {
        return !rewardItem.equals(Item.NONE);
    }

    public boolean requiresItem() {
        return !requireItemId.equals(Item.NONE.getId());
    }

    public boolean requiresFlag() {
        return !requireFlag.equals(FLAG_NONE);
    }

    public boolean generatesFlag() {
        return !generatedFlag.equals(FLAG_NONE);
    }

    public boolean isAvailable(Player player, Flag gameFlags) {
        if (requiresItem() && !player.getInventory().hasItem(requireItemId)) {
            return false;
        }

        return !requiresFlag() || gameFlags.isFlagActive(requireFlag);
    }

    public void applyEffects(Player player, Flag gameFlags) {
        if (trustLevelChange != 0) {
            if (trustLevelChange < 0)
                player.decreaseNpcTrustLevel(nameTargetNpc, trustLevelChange);
            else
                player.increaseNpcTrustLevel(nameTargetNpc, trustLevelChange);
        }

        if (sanityChange < 0)
            player.decreaseSanityLevel(sanityChange);
        else
            player.increaseSanityLevel(sanityChange);

        if (knowledgeChange < 0)
            player.decreaseKnowledgeLevel(knowledgeChange);
        else
            player.increaseKnowledgeLevel(knowledgeChange);

        if (hasRewardItem()) {
            player.storeItemInInventory(rewardItem);
        }

        if (generatesFlag()) {
            gameFlags.addFlag(generatedFlag);
        }
        if (requiresItem()) {
            player.getInventory().removeItem(this.requireItemId);
        }
    }

    public String getText() {
        return text;
    }
    public String getRequireFlag() {
        return requireFlag;
    }
    public String getRequireItemId() {
        return requireItemId;
    }
}
