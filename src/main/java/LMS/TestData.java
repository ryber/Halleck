package lms;

import halleck.Course;
import lms.learningobjects.Olt;

import java.util.List;

import static lms.learningobjects.LoUtils.newCourseList;

public class TestData {
    public static void hydrate(){
        CourseRepository.addCourse(getAllCourses());
    }

    static List<Course> getAllCourses() {
        return newCourseList(
                new Olt("CC1","Clean Code, Episode 1 - Clean Code","Get ready for something very different. This ain't no screen cast. This ain't no talkin' head lecture. This is an Uncle Bob Video!\n" +
                        "This is like watching Uncle Bob on stage, but more so. This is high content education that will hold your attention and stimulate your thoughts with its impactful and energetic style. So hold on to your hats and prepare yourself for a radically different kind of educational experience.\n" +
                        "\n" +
                        "In this video Uncle Bob shows why Clean Code is so important. He answers the question: Does Clean Code Matter? And he answers it emphatically in the affirmative! He explains how bad code leads to the downward spiral of The Productivity Trap. He describes the various ways and forms of Code Rot. And he concludes by answering the question: What is Clean Code?\n" +
                        "\n" +
                        "So don't wait. This one is almost a freebee. It's job is to convince you that you, your co-workers, and your managers will want (and need!) to see the rest. And besides, you don't really want to miss the astronomy lectures, do you?"),


                new Olt("CC2", "Clean Code, Episode 2 - Names++", "So, you think this is just going to be like the second chapter of the Clean Code book? Think again! This is a completely different take on the topic. Oh the goal is the same, certainly. But there are things covered in this video that were never mentioned in the book. And even those things that are similar are covered in a very different way. So even if you've read Clean Code fifty times over, this episode will give you more to think about.\n" +
                        "In this 40+ minute episode Uncle Bob will take you from the Earth to the Sun and then into the bowels of the Earth. You'll visit idyllic countrysides, urban back alleys, graffiti laden East London side streets, and even the Wild West as you explore the principles of names in your code.\n" +
                        "\n" +
                        "Despite the scenery, Uncle Bob never lets up on driving each point home so they become part of your daily discipline. He begins with the exhortation that names are not just a convenience for the programmer. They are the most powerful tool that programmers have to communicate with each other. Then he dives into a discussion on how to use names to Reveal Your Intent and Avoid Disinformation.\n" +
                        "\n" +
                        "From one code example to the next (showing up in the most unlikely places) you'll see Uncle Bob point out examples of bad names and good names. He'll explain why the good names are good, why the bad names are bad, and how to improve them.\n" +
                        "\n" +
                        "Uncle Bob will tell you how encoding schemes like Hungarian Notation began, and why you don't want to be using them in the twenty-first century. He'll go on to stress the importance of choosing names that make your code read like well written prose.\n" +
                        "\n" +
                        "Finally, Uncle Bob will tell you about The Scope Rule which will guide you in choosing function, class, and variable names of the appropriate length for the scope that contains them.\n" +
                        "\n" +
                        "So don't wait. If you want to be a clean coder. If you want to improve your skill and discipline, then hit the button and watch this episode now. Clean Code Episode II -- Names, is about to begin..."));
    }
}
