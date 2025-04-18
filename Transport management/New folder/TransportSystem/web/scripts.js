// Wait for the DOM to be fully loaded
document.addEventListener("DOMContentLoaded", function () {
  // Initialize navigation
  initNavigation();

  // Set current date as minimum date for journey
  const today = new Date();
  const formattedDate = today.toISOString().split("T")[0];
  document.getElementById("journey-date").min = formattedDate;

  // Initialize users if not exists
  if (!localStorage.getItem("users")) {
    // Create default admin user
    const defaultUsers = [
      {
        userId: "ADMIN001",
        username: "admin",
        password: "admin123",
        name: "System Admin",
        email: "admin@transport.com",
        phone: "1234567890",
        isAdmin: true,
      },
    ];
    localStorage.setItem("users", JSON.stringify(defaultUsers));
  }

  // Check if user is logged in
  checkLoginStatus();
});

// Check if user is logged in
function checkLoginStatus() {
  const currentUser = JSON.parse(localStorage.getItem("currentUser"));

  if (currentUser) {
    // Update UI for logged in user
    document
      .querySelectorAll('nav a[href="#login"], nav a[href="#register"]')
      .forEach((link) => {
        link.style.display = "none";
      });

    // Show My Bookings link
    document.querySelector('nav a[href="#my-bookings"]').style.display =
      "block";

    // Add logout link if not exists
    if (!document.querySelector('nav a[href="#logout"]')) {
      const navUl = document.querySelector("nav ul");
      const logoutLi = document.createElement("li");
      const logoutLink = document.createElement("a");
      logoutLink.href = "#logout";
      logoutLink.textContent = "Logout";
      logoutLink.addEventListener("click", function (e) {
        e.preventDefault();
        logout();
      });
      logoutLi.appendChild(logoutLink);
      navUl.appendChild(logoutLi);

      // Add user profile link
      const profileLi = document.createElement("li");
      const profileLink = document.createElement("a");
      profileLink.href = "#profile";
      profileLink.textContent = `Welcome, ${currentUser.name}`;
      profileLi.appendChild(profileLink);
      navUl.appendChild(profileLi);
    }
  } else {
    // Update UI for guest
    document
      .querySelectorAll('nav a[href="#login"], nav a[href="#register"]')
      .forEach((link) => {
        link.style.display = "block";
      });

    // Hide My Bookings link for guests
    document.querySelector('nav a[href="#my-bookings"]').style.display = "none";

    // Remove logout link if exists
    const logoutLink = document.querySelector('nav a[href="#logout"]');
    if (logoutLink) {
      logoutLink.parentElement.remove();
    }

    // Remove profile link if exists
    const profileLink = document.querySelector('nav a[href="#profile"]');
    if (profileLink) {
      profileLink.parentElement.remove();
    }
  }
}

// Initialize navigation
function initNavigation() {
  const navLinks = document.querySelectorAll("nav a");

  navLinks.forEach((link) => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      const targetId = this.getAttribute("href").substring(1);
      showSection(targetId);

      // Update active class
      navLinks.forEach((link) => link.classList.remove("active"));
      this.classList.add("active");
    });
  });
}

// Show a specific section
function showSection(sectionId) {
  // Check if user is logged in for protected sections
  const currentUser = JSON.parse(localStorage.getItem("currentUser"));
  const protectedSections = ["booking", "my-bookings"];

  if (protectedSections.includes(sectionId) && !currentUser) {
    alert("Please login to access this feature.");
    sectionId = "login";
  }

  // Load bookings if my-bookings section is shown
  if (sectionId === "my-bookings" && currentUser) {
    loadUserBookings();
  }

  // Hide all sections
  const sections = document.querySelectorAll(".section");
  sections.forEach((section) => {
    section.classList.remove("active");
  });

  // Show the target section
  const targetSection = document.getElementById(sectionId);
  if (targetSection) {
    targetSection.classList.add("active");

    // Update navigation active state
    const navLinks = document.querySelectorAll("nav a");
    navLinks.forEach((link) => {
      link.classList.remove("active");
      if (link.getAttribute("href") === "#" + sectionId) {
        link.classList.add("active");
      }
    });
  }
}

// Load user bookings
function loadUserBookings() {
  const currentUser = JSON.parse(localStorage.getItem("currentUser"));
  if (!currentUser) return;

  const bookingsContainer = document.getElementById("bookings-container");
  const noBookingsMessage = document.getElementById("no-bookings");

  // Get all bookings
  const allBookings = JSON.parse(localStorage.getItem("bookings")) || [];

  // Filter bookings for current user
  const userBookings = allBookings.filter(
    (booking) => booking.userId === currentUser.userId
  );

  // Clear previous bookings
  bookingsContainer.innerHTML = "";

  // Show no bookings message if no bookings found
  if (userBookings.length === 0) {
    bookingsContainer.appendChild(noBookingsMessage);
    return;
  } else {
    // Hide no bookings message
    if (noBookingsMessage) {
      noBookingsMessage.style.display = "none";
    }
  }

  // Get selected status filter
  const statusFilter = document.getElementById("booking-status").value;

  // Filter bookings by status if not "all"
  const filteredBookings =
    statusFilter === "all"
      ? userBookings
      : userBookings.filter((booking) => booking.status === statusFilter);

  // Sort bookings by date (newest first)
  filteredBookings.sort(
    (a, b) => new Date(b.bookingDate) - new Date(a.bookingDate)
  );

  // Create booking cards
  filteredBookings.forEach((booking) => {
    const bookingCard = document.createElement("div");
    bookingCard.className = "booking-card";

    // Format dates
    const bookingDate = new Date(booking.bookingDate);
    const journeyDate = new Date(booking.journeyDate);
    const formattedBookingDate =
      bookingDate.toLocaleDateString() + " " + bookingDate.toLocaleTimeString();
    const formattedJourneyDate = journeyDate.toLocaleDateString();

    // Create status badge
    const statusClass = booking.status.toLowerCase();

    bookingCard.innerHTML = `
      <div class="booking-status ${statusClass}">${booking.status}</div>
      <h3>${booking.source} to ${booking.destination}</h3>
      <div class="booking-details">
        <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
        <p><strong>Transport Type:</strong> ${booking.transportType.charAt(0).toUpperCase() + booking.transportType.slice(1)}</p>
        <p><strong>Journey Date:</strong> ${formattedJourneyDate}</p>
        <p><strong>Number of Seats:</strong> ${booking.seats}</p>
        <p><strong>Booking Date:</strong> ${formattedBookingDate}</p>
      </div>
      <div class="booking-actions">
        ${booking.status === "CONFIRMED" ? `<button class="btn btn-cancel" onclick="cancelUserBooking('${booking.bookingId}')">Cancel Booking</button>` : ""}
      </div>
    `;

    bookingsContainer.appendChild(bookingCard);
  });

  // Show message if no bookings match the filter
  if (filteredBookings.length === 0) {
    const noFilteredBookings = document.createElement("div");
    noFilteredBookings.className = "no-bookings";
    noFilteredBookings.innerHTML = `<p>No ${statusFilter.toLowerCase()} bookings found.</p>`;
    bookingsContainer.appendChild(noFilteredBookings);
  }
}

// Filter bookings by status
function filterBookings() {
  loadUserBookings();
}

// Cancel a user booking
function cancelUserBooking(bookingId) {
  if (!confirm("Are you sure you want to cancel this booking?")) {
    return;
  }

  // Get all bookings
  const allBookings = JSON.parse(localStorage.getItem("bookings")) || [];

  // Find booking index
  const bookingIndex = allBookings.findIndex(
    (booking) => booking.bookingId === bookingId
  );

  if (bookingIndex !== -1) {
    // Update booking status
    allBookings[bookingIndex].status = "CANCELLED";

    // Save updated bookings
    localStorage.setItem("bookings", JSON.stringify(allBookings));

    // Reload bookings
    loadUserBookings();

    alert("Booking cancelled successfully.");
  } else {
    alert("Booking not found.");
  }
}

// Search routes
function searchRoutes() {
  const source = document.getElementById("source").value;
  const destination = document.getElementById("destination").value;

  if (!source || !destination) {
    alert("Please select both source and destination");
    return;
  }

  // Filter route cards based on source and destination
  const routeCards = document.querySelectorAll(".route-card");
  routeCards.forEach((card) => {
    const routeTitle = card.querySelector("h4").textContent;
    const [routeSource, routeDestination] = routeTitle
      .split(" to ")
      .map((city) => city.trim());

    if (
      (routeSource === source && routeDestination === destination) ||
      (routeSource === destination && routeDestination === source)
    ) {
      card.style.display = "block";
    } else {
      card.style.display = "none";
    }
  });
}

// Show booking form
function showBookingForm(source, destination) {
  // Check if user is logged in
  const currentUser = JSON.parse(localStorage.getItem("currentUser"));
  if (!currentUser) {
    alert("Please login to book a ticket");
    showSection("login");
    return;
  }

  // Set source and destination in booking form
  document.getElementById("booking-source").value = source;
  document.getElementById("booking-destination").value = destination;
  document.getElementById("transport-type").value = "bus";

  // Show booking section
  showSection("booking");
}

// Book ticket
function bookTicket() {
  const source = document.getElementById("booking-source").value;
  const destination = document.getElementById("booking-destination").value;
  const transportType = document.getElementById("transport-type").value;
  const journeyDate = document.getElementById("journey-date").value;
  const seats = document.getElementById("seats").value;

  // Validate inputs
  if (!source || !destination || !transportType || !journeyDate || !seats) {
    alert("Please fill in all fields");
    return;
  }

  // Get current user
  const currentUser = JSON.parse(localStorage.getItem("currentUser"));
  if (!currentUser) {
    alert("Please login to book a ticket");
    showSection("login");
    return;
  }

  // Generate booking ID
  const bookingId = "BK" + Date.now().toString().slice(-6);

  // Create booking object
  const booking = {
    bookingId: bookingId,
    userId: currentUser.userId,
    source: source,
    destination: destination,
    transportType: transportType,
    journeyDate: journeyDate,
    seats: parseInt(seats),
    bookingDate: new Date().toISOString(),
    status: "CONFIRMED",
  };

  // Save booking
  const bookings = JSON.parse(localStorage.getItem("bookings")) || [];
  bookings.push(booking);
  localStorage.setItem("bookings", JSON.stringify(bookings));

  // Show success message
  alert("Booking confirmed! Your booking ID is: " + bookingId);

  // Redirect to my bookings
  showSection("my-bookings");
}

// Login function
function login() {
  const username = document.getElementById("login-username").value;
  const password = document.getElementById("login-password").value;

  if (!username || !password) {
    alert("Please enter both username and password.");
    return;
  }

  // Get users from localStorage
  const users = JSON.parse(localStorage.getItem("users")) || [];

  // Find user
  const user = users.find(
    (u) => u.username === username && u.password === password
  );

  if (user) {
    // Store current user in localStorage
    localStorage.setItem("currentUser", JSON.stringify(user));

    // Update UI
    checkLoginStatus();

    alert(`Login successful! Welcome, ${user.name}.`);
    showSection("home");

    // Clear login form
    document.getElementById("login-username").value = "";
    document.getElementById("login-password").value = "";
  } else {
    alert("Invalid username or password. Please try again.");
  }
}

// Logout function
function logout() {
  // Remove current user from localStorage
  localStorage.removeItem("currentUser");

  // Update UI
  checkLoginStatus();

  alert("You have been logged out successfully.");
  showSection("home");
}

// Register function
function register() {
  const username = document.getElementById("reg-username").value;
  const password = document.getElementById("reg-password").value;
  const name = document.getElementById("reg-name").value;
  const email = document.getElementById("reg-email").value;
  const phone = document.getElementById("reg-phone").value;

  if (!username || !password || !name || !email || !phone) {
    alert("Please fill in all fields.");
    return;
  }

  // Get users from localStorage
  const users = JSON.parse(localStorage.getItem("users")) || [];

  // Check if username already exists
  if (users.some((u) => u.username === username)) {
    alert("Username already exists. Please choose a different username.");
    return;
  }

  // Create new user
  const newUser = {
    userId: "USER" + Date.now(),
    username: username,
    password: password,
    name: name,
    email: email,
    phone: phone,
    isAdmin: false,
  };

  // Add user to users array
  users.push(newUser);

  // Save users to localStorage
  localStorage.setItem("users", JSON.stringify(users));

  alert(
    `Registration successful! Welcome, ${name}. Please login with your credentials.`
  );

  // Reset form
  document.getElementById("reg-username").value = "";
  document.getElementById("reg-password").value = "";
  document.getElementById("reg-name").value = "";
  document.getElementById("reg-email").value = "";
  document.getElementById("reg-phone").value = "";

  // Redirect to login
  showSection("login");
}
