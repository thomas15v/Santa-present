package Santa_Present;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener  {
	
	Material[] items;
	Map<String, Date> players = new HashMap<String, Date>();
	
	
	@Override
	public void onEnable() {
		Set<Material> Items = new HashSet<Material>();
		for (Material b : Material.values()){
			if (!b.isBlock()){
				Items.add(b);
			}
		}
		
		this.items = Items.toArray(new Material[Items.size()]);
		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOWEST)
	void PlayerInteractEvent(PlayerInteractEvent event){
		if (event.getClickedBlock().getType() == Material.WOOL || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (new Wool(event.getClickedBlock().getTypeId(), event.getClickedBlock().getData()).getColor() == DyeColor.RED){
				Date date = new Date();
				long datediff = 31;
				
				if (players.containsKey(event.getPlayer().getName())){
					datediff = getDateDiff(players.get(event.getPlayer().getName()), date, TimeUnit.SECONDS);
				}
				
				if (datediff > 30){
					Random random = new Random();
					Material item = items[random.nextInt(items.length)];			
					event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(item , 1));
					players.put(event.getPlayer().getName(), new Date());
					event.getPlayer().sendMessage(ChatColor.GREEN + "Santa came :D!");
				}
				else{
					event.getPlayer().sendMessage(ChatColor.RED + "Santa is busy!");
					
				}
				
				
				
			}
		}	
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
