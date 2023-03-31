package com.example.qr_quest.unitTests;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.junit.Assert.*;

import com.example.qr_quest.ItemClickListener;
import com.example.qr_quest.WalletAdapter;

public class WalletAdapterTest {

    private Wallet[] testWallets;
    private WalletAdapter testAdapter;

    @Mock
    private ItemClickListener mockClickListener;

    @Before
    public void setup() {
        testWallets = new Wallet[]{
                new Wallet("Wallet 1", "100", "testImage1"),
                new Wallet("Wallet 2", "200", "testImage2"),
                new Wallet("Wallet 3", "300", "testImage3")
        };
        testAdapter = new WalletAdapter(testWallets);
        testAdapter.setClickListener(mockClickListener);
    }

    @Test
    public void testGetItemCount() {
        assertEquals(3, testAdapter.getItemCount());
    }
}
