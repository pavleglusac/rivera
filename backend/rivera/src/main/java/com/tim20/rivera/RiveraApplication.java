package com.tim20.rivera;

import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import com.tim20.rivera.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.tim20.rivera")
public class RiveraApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiveraApplication.class, args);
	}

	@Autowired
	private AdventureRepository adventureRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private PricelistRepository pricelistRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private FishingInstructorRepository fishingInstructorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	private void initializeData2() {
		Adventure adventure = new Adventure();
		adventure.setName("Very Cool Fishing");
		adventure.setAddress("1234 Main St.");
		adventure.setCity("Zrenjanin");
		adventure.setCountry("Serbia");
		adventure.setDescription("Pellentesque habitant morbi tristique senectus et netus et malesuada" +
				" fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum" +
				" maximus quam mollis consequat");
		adventure.setPictures(Arrays.asList("/images/adventures/1/img5.jpg",
				"/images/adventures/1/img4.jpg",
				"/images/adventures/1/img3.jpg",
				"/images/adventures/1/img2.jpg",
				"/images/adventures/1/img1.jpg"));

		//adventure.setTags(Arrays.asList(tagRepository.findByName("wifi").get(), tagRepository.findByName("river").get()));
		Pricelist pricelist = new Pricelist();
		pricelist.setStartDateTime(LocalDateTime.now());
		pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
		pricelist.setPricePerHour(10.0);
		pricelist.setPricePerDay(40.0);
		pricelist.setCancellationTerms("20");
		pricelist.setRentable(adventure);
		adventure.setPricelists(Arrays.asList(pricelist));
		adventure.setCurrentPricelist(pricelist);
		pricelistRepository.save(pricelist);

		adventure.setCapacity(5);

		adventure.setFishingEquipment(Arrays.asList("Rod 123", "Silk 4", "Golden Bait"));
		adventure.setRulesOfConduct(Arrays.asList("no smoking", "no destruction of property"));
		adventure.setAverageScore(3.3);

		Discount discount1 = new Discount();
		discount1.setCapacity(3);
		discount1.setStartDateTime(LocalDateTime.of(2022, 8, 12, 16, 0 ));
		discount1.setEndDateTime(LocalDateTime.of(2022, 8, 13, 1, 0 ));
		discount1.setPrice(300.0);
		//discount1.setTags(Arrays.asList(tagRepository.findByName("canoe").get(), tagRepository.findByName("extreme").get()));
		discount1.setRentable(adventure);

		Review review1 = new Review();
		review1.setRentable(adventure);
		review1.setPosted(LocalDateTime.now());
		review1.setText("Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, " +
				"in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ");
		review1.setScore(3.3);
		review1.setClient(clientRepository.findClientByUsername("pera").get());
		review1.setStatus(ReviewStatus.ACCEPTED);
		reviewRepository.save(review1);

		adventure.setOwner(fishingInstructorRepository.findByUsername("marko").get());
		adventureRepository.save(adventure);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void initializeData() {
		Adventure adventure = new Adventure();
		adventure.setName("Mountain Fishing");
		adventure.setAddress("7 Grove St.");
		adventure.setCity("Cairns");
		adventure.setCountry("Australia");
		adventure.setDescription("Pellentesque habitant morbi tristique senectus et netus et malesuada" +
				" fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum" +
				" maximus quam mollis consequat");
		adventure.setPictures(Arrays.asList("/images/adventures/1/img1.jpg",
											"/images/adventures/1/img2.jpg",
											"/images/adventures/1/img3.jpg",
											"/images/adventures/1/img4.jpg",
											"/images/adventures/1/img5.jpg"));
		Tag tagWifi = new Tag(); tagWifi.setName("wifi");
		Tag tagRiver = new Tag(); tagRiver.setName("river");
		Tag tagExtreme = new Tag(); tagExtreme.setName("extreme");
		Tag tagCanoe = new Tag(); tagCanoe.setName("canoe");
		tagRepository.saveAll(Arrays.asList(tagWifi, tagRiver, tagCanoe, tagExtreme));

		//adventure.setTags(Arrays.asList(tagWifi, tagExtreme, tagRiver));

		Pricelist pricelist = new Pricelist();
		pricelist.setStartDateTime(LocalDateTime.now());
		pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
		pricelist.setPricePerHour(10.0);
		pricelist.setPricePerDay(40.0);
		pricelist.setCancellationTerms("20");
		pricelist.setRentable(adventure);
		adventure.setPricelists(Arrays.asList(pricelist));
		adventure.setCurrentPricelist(pricelist);
		pricelistRepository.save(pricelist);

		adventure.setCapacity(4);

		adventure.setFishingEquipment(Arrays.asList("Rod 123", "Silk 4", "Golden Bait"));
		adventure.setRulesOfConduct(Arrays.asList("no smoking", "no destruction of property"));
		adventure.setAverageScore(3.3);

		Discount discount1 = new Discount();
		discount1.setCapacity(3);
		discount1.setStartDateTime(LocalDateTime.of(2022, 8, 12, 16, 0 ));
		discount1.setEndDateTime(LocalDateTime.of(2022, 8, 13, 1, 0 ));
		discount1.setPrice(300.0);
		//discount1.setTags(Arrays.asList(tagCanoe, tagExtreme));
		discount1.setRentable(adventure);

		Discount discount2 = new Discount();
		discount2.setCapacity(4);
		discount2.setStartDateTime(LocalDateTime.of(2022, 8, 18, 16, 0 ));
		discount2.setEndDateTime(LocalDateTime.of(2022, 8, 18, 17, 0 ));
		discount2.setPrice(450.0);
		//
		// discount2.setTags(Arrays.asList(tagCanoe, tagExtreme));
		discount2.setRentable(adventure);

		adventure.setDiscounts(Arrays.asList(discount1, discount2));
		discountRepository.saveAll(Arrays.asList(discount1, discount2));

		Client client1 = new Client();
		client1.setUsername("pera");
		client1.setAddress("Dobrovoljacka 35");
		client1.setCity("Zrenjanin");
		client1.setCountry("Serbia");
		client1.setName("Petar");
		client1.setSurname("Petrovic");
		client1.setDeleted(false);
		client1.setEmail("pera@gmail.com");
		client1.setPassword(passwordEncoder.encode("sifra"));
		client1.setPhoneNumber("+3815565456");
		client1.setPhoto("/images/clients/" + client1.getUsername() + ".jpg");
		clientRepository.save(client1);

		Client client2 = new Client();
		client2.setUsername("mika");
		client2.setAddress("Dobrovoljacka 37");
		client2.setCity("Zrenjanin");
		client2.setCountry("Serbia");
		client2.setName("Mika");
		client2.setSurname("Mikic");
		client2.setDeleted(false);
		client2.setEmail("mika@gmail.com");
		client2.setPassword(passwordEncoder.encode("sifra"));
		client2.setPhoneNumber("+3815565456");
		client2.setPhoto("/images/clients/" + client1.getUsername() + ".jpg");
		clientRepository.save(client2);

		Review review1 = new Review();
		review1.setRentable(adventure);
		review1.setPosted(LocalDateTime.now());
		review1.setText("Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, " +
						"in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ");
		review1.setScore(3.3);
		review1.setClient(client1);
		review1.setStatus(ReviewStatus.ACCEPTED);
		reviewRepository.save(review1);

		Review review2 = new Review();
		review2.setRentable(adventure);
		review2.setPosted(LocalDateTime.now());
		review2.setText("Ut mollis erat non quam rhoncus sodales. Nulla facilisi. Duis sit amet pellentesque dolor. Aliquam mollis rhoncus " +
						"risus vel ornare. Nulla pellentesque risus ac lorem efficitur auctor. Cras auctor turpis");
		review2.setScore(3.3);
		review2.setClient(client2);
		review2.setStatus(ReviewStatus.ACCEPTED);
		reviewRepository.save(review2);

		adventure.setReviews(Arrays.asList(review1, review2));


		Reservation reservation = new Reservation();
		reservation.setClient(client1);
		reservation.setRentable(adventure);
		reservation.setStartDateTime(LocalDateTime.now().plusHours(1));
		reservation.setEndDateTime(LocalDateTime.now().plusHours(2));
		reservation.setPrice(130.0);
		reservation.setCancelled(false);

		FishingInstructor fishingInstructor = new FishingInstructor();
		fishingInstructor.setName("Marko");
		fishingInstructor.setSurname("Markovic");
		fishingInstructor.setAddress("Njegoseva 35");
		fishingInstructor.setCity("Zrenjanin");
		fishingInstructor.setCountry("Serbia");
		fishingInstructor.setStatus(AccountStatus.ACTIVE);
		fishingInstructor.setDeleted(false);
		fishingInstructor.setBiography("Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. " +
										"Maecenas nec quam tellus ex non nisl" +
										"x diam, sed euismod augue dignissim ut. Aenean non rhoncus ante.");
		fishingInstructor.setEmail("marko@gmail.com");
		fishingInstructor.setPassword(passwordEncoder.encode("sifra"));
		fishingInstructor.setPhoneNumber("+3845135535");
		fishingInstructor.setUsername("marko");
		fishingInstructor.setPhoto("/images/clients/" + fishingInstructor.getUsername() + ".jpg");
		fishingInstructor.setAdventures(Arrays.asList(adventure));


		Role role = new Role();
		Role role2 = new Role();

		role.setName("ROLE_COTTAGE_OWNER");
		role.setId(1l);
		role2.setName("ROLE_BOAT_OWNER");
		role2.setId(2l);
		roleRepository.save(role);
		roleRepository.save(role2);

		Role role3 = new Role();
		role3.setName("ROLE_FISHING_INSTRUCTOR");
		role3.setId(3l);
		roleRepository.save(role3);

		List<Role> roles = roleService.findByName("ROLE_FISHING_INSTRUCTOR");
		fishingInstructor.setRoles(roles);

		fishingInstructorRepository.save(fishingInstructor);
		adventure.setOwner(fishingInstructor);

		adventure.setAdditionalServices(Arrays.asList("massage", "therapy", "basketball"));

		reservationRepository.save(reservation);
		adventureRepository.save(adventure);



		initializeData2();
	}

}
