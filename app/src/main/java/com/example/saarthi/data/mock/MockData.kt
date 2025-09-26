package com.example.saarthi.data.mock

import com.example.saarthi.data.model.*

object MockData {

    fun getMockCareers(): List<Career> {
        return listOf(
            Career(1, "AI/ML Engineer", "A dynamic field combining computer science and advanced mathematics.", listOf("Python", "TensorFlow", "PyTorch", "Algorithms"), "₹15 - 25 LPA"),
            Career(2, "Data Scientist", "Analyzing complex data to find trends and insights.", listOf("SQL", "R", "Statistics", "Machine Learning"), "₹12 - 20 LPA"),
            Career(3, "Software Developer", "Designing, building, and maintaining software applications.", listOf("Java", "Kotlin", "Git", "API Design"), "₹8 - 18 LPA"),
            Career(4, "Product Manager", "Guiding the success of a product and leading the cross-functional team.", listOf("Roadmapping", "Agile", "User Research", "Communication"), "₹18 - 30 LPA"),
            Career(5, "UI/UX Designer", "Creating user-friendly interfaces for complex technical products.", listOf("Figma", "Prototyping", "Wireframing", "User Testing"), "₹7 - 15 LPA")
        )
    }

    fun getMockColleges(): List<College> {
        return listOf(
            College(1, "University of Kashmir", "Srinagar, J&K", "A premier institution of higher learning in the region, known for its beautiful campus and diverse academic programs.", listOf("M.A. English", "M.Sc. Physics", "MBA"), listOf("Hostel", "Library", "Wi-Fi Campus")),
            College(2, "University of Jammu", "Jammu, J&K", "A large, multi-faculty university offering a wide range of undergraduate and postgraduate courses.", listOf("B.Com", "M.A. Sociology", "LL.B"), listOf("Sports Complex", "Central Library", "Health Centre")),
            College(3, "NIT Srinagar", "Srinagar, J&K", "An institute of national importance, focusing on engineering and technology education and research.", listOf("B.Tech CSE", "B.Tech Mechanical", "M.Tech Structures"), listOf("Advanced Labs", "Hostel", "Gymnasium")),
            College(4, "Shri Mata Vaishno Devi University", "Katra, J&K", "A residential university known for its technical education and scenic location.", listOf("B.Tech Computer Science", "MBA", "M.Sc. Biotechnology"), listOf("Modern Hostels", "E-Library", "Auditorium"))
            // You can add more details for the other colleges as well
        )
    }

    fun getMockDeadlines(): List<Deadline> {
        return listOf(
            Deadline("University Application", "November 15, 2025", "University"),
            Deadline("Scholarship Submission", "December 1, 2025", "Scholarship"),
            Deadline("Career Fair Registration", "October 20, 2025", "Career Fair")
        )
    }
}