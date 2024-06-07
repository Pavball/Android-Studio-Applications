const express = require('express');
const app = express();

app.get('/exercises', (req, res) => {
    const exercises = [
        {
            name: "Deadlift",
            description: "The deadlift is a weight training exercise in which a loaded barbell or bar is lifted off the ground to the level of the hips, then lowered to the ground.",
            technique: "Stand with feet about hip-width apart, keeping the bar close to your shins. Grip the bar with hands shoulder-width apart. Keep your back straight as you lift the bar by straightening your hips and knees.",
            imageUrl: "https://i.ytimg.com/vi/1ZXobu7JvvE/hqdefault.jpg",
            websiteUrl: "https://www.verywellfit.com/how-to-do-the-deadlift-3498608"
        },
        {
            name: "Squat",
            description: "The squat is a compound, full body exercise that trains primarily the muscles of the thighs, hips and buttocks, quadriceps femoris muscle, hamstrings, as well as strengthening the bones, ligaments and insertion of the tendons throughout the lower body.",
            technique: "Stand with feet shoulder-width apart, toes pointed slightly outwards. Lower your body by bending your knees and hips, keeping your chest up and back straight. Squat until your thighs are parallel to the ground, then push back up to starting position.",
            imageUrl: "https://www.isischiropractic.co.uk/wp-content/uploads/2023/05/squatting-powerlifting-injuries.jpg",
            websiteUrl: "https://www.realsimple.com/health/fitness-exercise/workouts/squat-form"
        }
        // Add more exercise objects as needed
    ];
  res.json(exercises);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));




