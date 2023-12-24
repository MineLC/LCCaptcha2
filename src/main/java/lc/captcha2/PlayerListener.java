package lc.captcha2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static lc.captcha2.LCCaptcha2.sendTitle;

public class PlayerListener implements Listener {


    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String barr = ChatColor.translateAlternateColorCodes('&', "&7&m-------------------------");
      String msg = ChatColor.translateAlternateColorCodes('&', "&fHola &e" + p.getName() + ", &fpara poder ingresar al servidor");
        String msg2 = ChatColor.translateAlternateColorCodes('&', "&fprimero debes escribir el comando que ves en la pantalla.");
        String barr2 = ChatColor.translateAlternateColorCodes('&', "&7&m-------------------------");
     
        p.sendMessage(barr);
        p.sendMessage(msg);
        p.sendMessage(msg2);
        p.sendMessage(barr2);
        LCCaptcha2.playerkick.add(p);
        LCCaptcha2.player_catpchat.put(p, LCCaptcha2.generateRandomWord(5));
      Bukkit.getConsoleSender().sendMessage(p.getName() + " captcha: " + LCCaptcha2.player_catpchat.get(p));

      new BukkitRunnable(){

          @Override
          public void run() {
              if(p == null){
                  cancel();
                  return;
              }
              if(!LCCaptcha2.playerkick.contains(p)){
                  cancel();
                  return;
              }
              sendTitle(p, ChatColor.translateAlternateColorCodes('&', "&6Escribe: "), 5,5, 5);
              LCCaptcha2.sendSubTitle(p, ChatColor.translateAlternateColorCodes('&' , "&a/captcha " + LCCaptcha2.player_catpchat.get(p)), 5, 5,5);

          }
      }.runTaskTimerAsynchronously(LCCaptcha2.getPlugin(LCCaptcha2.class), 0, 20);

      new BukkitRunnable(){

          @Override
          public void run() {
            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4Demoraste demasiado, vuelve a intentarlo."));
          }
      }.runTaskLater(LCCaptcha2.getPlugin(LCCaptcha2.class), 900);
    }


    @EventHandler
    public void playerleave(PlayerQuitEvent e){
        LCCaptcha2.playerkick.remove(e.getPlayer());
        LCCaptcha2.player_catpchat.remove(e.getPlayer());
    }
}
