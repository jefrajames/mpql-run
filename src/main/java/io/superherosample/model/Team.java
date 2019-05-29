package io.superherosample.model;

import java.util.List;
import org.eclipse.microprofile.graphql.FieldsOrder;
import org.eclipse.microprofile.graphql.Type;

@Type(value = "Team",  description = "A team made of super heros")
@FieldsOrder({"name", "members"})
public class Team {


	private String name;
	private List<SuperHero> members;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SuperHero> getMembers() {
		return members;
	}
	public void setMembers(List<SuperHero> members) {
		this.members = members;
	}
	public Team addMembers(SuperHero...heroes) {
		for (SuperHero hero : heroes) {
			members.add(hero);
		}
		return this;
	}
	public Team removeMembers(SuperHero...heroes) {
		for (SuperHero hero : heroes) {
			members.remove(hero);
		}
		return this;
	}
}
