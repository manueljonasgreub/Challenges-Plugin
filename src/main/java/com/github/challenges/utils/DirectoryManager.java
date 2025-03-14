package com.github.challenges.utils;

import org.bukkit.Bukkit;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class DirectoryManager {
    public static void resetWorldDirectory() {
        try {

            File world = new File(Bukkit.getWorldContainer(), "world");
            File world_nether = new File(Bukkit.getWorldContainer(), "world_nether");
            File world_the_end = new File(Bukkit.getWorldContainer(), "world_the_end");


            FileUtils.deleteDirectory(world);
            FileUtils.deleteDirectory(world_nether);
            FileUtils.deleteDirectory(world_the_end);

            world.mkdirs();
            world_nether.mkdirs();
            world_the_end.mkdirs();

            new File(world, "data").mkdirs();
            new File(world, "datapacks").mkdirs();
            new File(world, "playerdata").mkdirs();
            new File(world, "poi").mkdirs();
            new File(world, "region").mkdirs();

            new File(world_nether, "data").mkdirs();
            new File(world_nether, "datapacks").mkdirs();
            new File(world_nether, "playerdata").mkdirs();
            new File(world_nether, "poi").mkdirs();
            new File(world_nether, "region").mkdirs();

            new File(world_the_end, "data").mkdirs();
            new File(world_the_end, "datapacks").mkdirs();
            new File(world_the_end, "playerdata").mkdirs();
            new File(world_the_end, "poi").mkdirs();
            new File(world_the_end, "region").mkdirs();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

