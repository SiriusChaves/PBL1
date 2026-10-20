package main.model.npc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NpcTest {

    @Test
    void npcShouldStartNeutral() {

        Npc npc = new Npc("Gabriel");

        assertEquals(50, npc.getTrustLevel());
        assertEquals("NEUTRO", npc.getRelationshipTier());
    }

    @Test
    void trustShouldRespectLimits() {

        Npc npc = new Npc("Gabriel");

        npc.increaseTrustLevel(100);
        assertEquals(100, npc.getTrustLevel());

        npc.decreaseTrustLevel(150);
        assertEquals(0, npc.getTrustLevel());
    }

    @Test
    void decreaseTrustShouldUseAbsoluteValue() {

        Npc npc = new Npc("Gabriel");

        npc.decreaseTrustLevel(-20);

        assertEquals(30, npc.getTrustLevel());
    }

    @Test
    void relationshipTiersShouldRespectBoundaries() {

        assertEquals(
                "EM SUSPEITA",
                new Npc("A", 24).getRelationshipTier()
        );

        assertEquals(
                "NEUTRO",
                new Npc("A", 25).getRelationshipTier()
        );

        assertEquals(
                "NEUTRO",
                new Npc("A", 59).getRelationshipTier()
        );

        assertEquals(
                "ALIADO PLENO",
                new Npc("A", 60).getRelationshipTier()
        );
    }

    @Test
    void equalityAndComparisonShouldUseName() {

        Npc gabrielA = new Npc("Gabriel", 10);
        Npc gabrielB = new Npc("Gabriel", 90);
        Npc lia = new Npc("Lia");

        assertEquals(gabrielA, gabrielB);
        assertEquals(gabrielA.hashCode(), gabrielB.hashCode());
        assertTrue(gabrielA.compareTo(lia) < 0);
    }
}
