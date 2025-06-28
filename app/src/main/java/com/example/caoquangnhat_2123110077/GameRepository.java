// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameRepository.java
package com.example.caoquangnhat_2123110077;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameRepository {

    private static List<Game> allGamesCache;

    public static List<Game> getAllGames() {
        if (allGamesCache == null) {
            allGamesCache = new ArrayList<>();
            setupGameData();
        }
        return allGamesCache;
    }

    private static void setupGameData() {
        allGamesCache.clear();
        // --- CẬP NHẬT DỮ LIỆU: THÊM THAM SỐ THỨ 7 (SYSTEM REQUIREMENTS) ---
        allGamesCache.add(new Game("Elden Ring", "990.000đ",
                Arrays.asList(R.drawable.elden_ring, R.drawable.er_gp1,R.drawable.er_gp2,R.drawable.er_gp3,R.drawable.er_gp4,R.drawable.er_gp5),
                "Nhập vai",
                "THE NEW FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.",
                4.8f,
                "HĐH: Windows 10\nBộ xử lý: INTEL CORE I5-8400 or AMD RYZEN 3 3300X\nRAM: 12 GB\nĐồ họa: NVIDIA GEFORCE GTX 1060 3 GB or AMD RADEON RX 580 4 GB\nLưu trữ: 60 GB dung lượng trống"
        ));
        allGamesCache.add(new Game("Baldur's Gate 3", "850.000đ",
                Arrays.asList(R.drawable.baldurs_gate_3, R.drawable.bg_gp1,R.drawable.bg_gp2,R.drawable.bg_gp3,R.drawable.bg_gp4,R.drawable.bg_gp5),
                "Nhập vai",
                "Gather your party, and return to the Forgotten Realms in a tale of fellowship and betrayal, sacrifice and survival, and the lure of absolute power.",
                4.9f,
                "HĐH: Windows 10 64-bit\nBộ xử lý: Intel I5 4690 / AMD FX 8350\nRAM: 8 GB\nĐồ họa: Nvidia GTX 970 / RX 480 (4GB+ of VRAM)\nLưu trữ: 150 GB dung lượng trống"
        ));
        allGamesCache.add(new Game("Cyberpunk 2077", "990.000đ",
                Arrays.asList(R.drawable.cyberpunk_2077, R.drawable.cyberpunk_gp1, R.drawable.cyberpunk_gp2, R.drawable.cyberpunk_gp3, R.drawable.cyberpunk_gp4),
                "Hành động",
                "Cyberpunk 2077 is an open-world, action-adventure story set in Night City, a megalopolis obsessed with power, glamour and body modification. You play as V, a mercenary outlaw going after a one-of-a-kind implant that is the key to immortality.",
                4.2f,
                "HĐH: Windows 10 64-bit\nBộ xử lý: Core i7-6700 or Ryzen 5 1600\nRAM: 12 GB\nĐồ họa: GeForce GTX 1060 6GB or Radeon RX 580 8GB\nLưu trữ: 70 GB SSD dung lượng trống"
        ));
        allGamesCache.add(new Game("Stardew Valley", "165.000đ",
                Arrays.asList(R.drawable.stardew_valley, R.drawable.stardew_gp1,R.drawable.stardew_gp2,R.drawable.stardew_gp3,R.drawable.stardew_gp4,R.drawable.stardew_gp5),
                "Chiến thuật",
                "You've inherited your grandfather's old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home?",
                4.9f,
                "HĐH: Windows Vista trở lên\nBộ xử lý: 2.0 GHz\nRAM: 2 GB\nĐồ họa: 256 MB video memory, shader model 3.0+\nLưu trữ: 500 MB dung lượng trống"
        ));
        allGamesCache.add(new Game("The Witcher 3", "450.000đ",
                Arrays.asList(R.drawable.the_witcher_3,R.drawable.w3_gp1,R.drawable.w3_gp2,R.drawable.w3_gp3,R.drawable.w3_gp4,R.drawable.w3_gp5),
                "Nhập vai",
                "As war rages on throughout the Northern Realms, you take on the greatest contract of your life — tracking down the Child of Prophecy, a living weapon that can alter the shape of the world.",
                4.8f,
                "HĐH: 64-bit Windows 7 or 64-bit Windows 8 (8.1)\nBộ xử lý: Intel CPU Core i5-2500K 3.3GHz / AMD CPU Phenom II X4 940\nRAM: 6 GB\nĐồ họa: Nvidia GPU GeForce GTX 660 / AMD GPU Radeon HD 7870\nLưu trữ: 35 GB dung lượng trống"
        ));
        allGamesCache.add(new Game("DOOM Eternal", "750.000đ",
                Arrays.asList(R.drawable.doom_eternal,R.drawable.d_gp1,R.drawable.d_gp2,R.drawable.d_gp3,R.drawable.d_gp4,R.drawable.d_gp5),
                "Hành động",
                "Hell’s armies have invaded Earth. Become the Slayer in an epic single-player campaign to conquer demons across dimensions and stop the final destruction of humanity.",
                4.6f,
                "HĐH: 64-bit Windows 10\nBộ xử lý: Intel Core i7-6700K or better, or AMD Ryzen 7 1800X or better\nRAM: 16 GB\nĐồ họa: NVIDIA GeForce GTX 1080 (8GB), RTX 2060 (8GB) or AMD Radeon RX Vega 56 (8GB)\nLưu trữ: 80 GB dung lượng trống"
        ));
        allGamesCache.add(new Game("The Last of Us Part II", "1.250.000đ",
                Arrays.asList(R.drawable.the_last_of_us_2,R.drawable.tlou_gp1,R.drawable.tlou_gp2,R.drawable.tlou_gp3,R.drawable.tlou_gp4,R.drawable.tlou_gp5),
                "Hành động",
                "Five years after their dangerous journey across the post-pandemic United States, Ellie and Joel have settled down in Jackson, Wyoming. Living amongst a thriving community of survivors has allowed them peace and stability, despite the constant threat of the infected and other, more desperate survivors.",
                4.5f,
                "HĐH: Windows 10 64-bit\nBộ xử lý: Intel Core i7-4770K / AMD Ryzen 5 1500X\nRAM: 16 GB\nĐồ họa: NVIDIA GeForce GTX 1060 (6 GB) / AMD Radeon RX 480 (4 GB)\nLưu trữ: 100 GB dung lượng trống"
        ));
    }
}