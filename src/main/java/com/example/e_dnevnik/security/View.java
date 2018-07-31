package com.example.e_dnevnik.security;

public class View {
	
	public static class UcenikPogled {
	};

	public static class RoditeljPogled extends UcenikPogled {
	};

	public static class NastavnikPogled extends RoditeljPogled {
	};

	public static class AdministratorPogled extends NastavnikPogled {
	};

}
