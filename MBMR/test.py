import tkinter as tk
from tkinter import ttk, scrolledtext
import random
from tkinter.font import Font
import json
import webbrowser
import os

# Load songs from JSON file
def load_songs():
    try:
        with open('songs_data.json', 'r', encoding='utf-8') as file:
            return json.load(file)
    except FileNotFoundError:
        return {}

class MusicRecommenderGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Mood-Based Music Recommender")
        self.root.geometry("1000x700")  # Increased size for more content
        self.root.configure(bg="#2C3E50")
        
        # Load songs
        self.mood_songs = load_songs()
        
        # Style configuration
        self.setup_styles()
        
        # Main container
        main_frame = tk.Frame(root, bg="#2C3E50")
        main_frame.pack(expand=True, fill='both', padx=20, pady=20)
        
        self.setup_header(main_frame)
        self.setup_mood_selection(main_frame)
        self.setup_buttons(main_frame)
        self.setup_results_area(main_frame)
        self.setup_footer(main_frame)

    def setup_styles(self):
        style = ttk.Style()
        style.theme_use('clam')
        style.configure("Custom.TButton",
                       padding=10,
                       font=('Helvetica', 12),
                       background="#3498DB",
                       foreground="white")
        style.configure("Custom.TLabel",
                       padding=6,
                       font=('Helvetica', 12),
                       background="#2C3E50",
                       foreground="white")

    def setup_header(self, parent):
        header_font = Font(family="Helvetica", size=24, weight="bold")
        header = tk.Label(
            parent,
            text="🎵 Mood-Based Music Recommender 🎵",
            font=header_font,
            bg="#2C3E50",
            fg="#ECF0F1"
        )
        header.pack(pady=20)

    def setup_mood_selection(self, parent):
        mood_frame = tk.Frame(parent, bg="#2C3E50")
        mood_frame.pack(pady=20)
        
        tk.Label(
            mood_frame,
            text="Select your mood:",
            font=('Helvetica', 14),
            bg="#2C3E50",
            fg="#ECF0F1"
        ).pack(side=tk.LEFT, padx=10)
        
        self.mood_var = tk.StringVar()
        self.mood_dropdown = ttk.Combobox(
            mood_frame,
            textvariable=self.mood_var,
            values=list(self.mood_songs.keys()),
            state='readonly',
            width=20,
            font=('Helvetica', 12)
        )
        self.mood_dropdown.pack(side=tk.LEFT, padx=10)

    def setup_buttons(self, parent):
        button_frame = tk.Frame(parent, bg="#2C3E50")
        button_frame.pack(pady=10)
        
        buttons = [
            ("Get Recommendations", self.show_recommendations, "#3498DB"),
            ("I'm Feeling Lucky! 🎲", self.random_recommendation, "#2ECC71"),
            ("Play Selected Song 🎵", self.play_selected_song, "#E74C3C")
        ]
        
        for text, command, color in buttons:
            tk.Button(
                button_frame,
                text=text,
                command=command,
                bg=color,
                fg="white",
                font=('Helvetica', 12, 'bold'),
                padx=20,
                pady=10,
                relief=tk.RAISED,
                cursor="hand2"
            ).pack(side=tk.LEFT, padx=10)

    def setup_results_area(self, parent):
        self.result_text = scrolledtext.ScrolledText(
            parent,
            wrap=tk.WORD,
            width=70,
            height=20,
            font=('Helvetica', 11),
            bg="#ECF0F1",
            fg="#2C3E50"
        )
        self.result_text.pack(pady=20, padx=20)
        
        # Add tag configuration for clickable links
        self.result_text.tag_configure("link", foreground="blue", underline=1)
        self.result_text.tag_bind("link", "<Button-1>", self.on_link_click)
        self.result_text.tag_bind("link", "<Enter>", lambda e: self.result_text.configure(cursor="hand2"))
        self.result_text.tag_bind("link", "<Leave>", lambda e: self.result_text.configure(cursor=""))

    def setup_footer(self, parent):
        tk.Label(
            parent,
            text="✨ Click on any song to play it! ✨",
            font=('Helvetica', 10),
            bg="#2C3E50",
            fg="#ECF0F1"
        ).pack(pady=10)

    def show_recommendations(self):
        mood = self.mood_var.get().lower()
        if not mood:
            self.result_text.delete(1.0, tk.END)
            self.result_text.insert(tk.END, "🎵 Please select a mood first! 🎵")
            return
            
        self.result_text.delete(1.0, tk.END)
        self.result_text.insert(tk.END, f"🎵 Here are some {mood} songs for you: 🎵\n\n")
        
        for i, song in enumerate(self.mood_songs[mood], 1):
            self.result_text.insert(tk.END, f"{i}. {song['title']} - {song['artist']}\n", "link")
            self.result_text.insert(tk.END, "\n")

    def random_recommendation(self):
        mood = random.choice(list(self.mood_songs.keys()))
        self.mood_var.set(mood)
        self.show_recommendations()

    def play_selected_song(self):
        try:
            selection = self.result_text.tag_ranges(tk.SEL)
            if selection:
                selected_text = self.result_text.get(selection[0], selection[1])
                self.play_song(selected_text)
        except tk.TclError:
            self.result_text.insert(tk.END, "\n⚠️ Please select a song first!\n")

    def on_link_click(self, event):
        index = self.result_text.index(f"@{event.x},{event.y}")
        line_start = self.result_text.index(f"{index} linestart")
        line_end = self.result_text.index(f"{index} lineend")
        line_text = self.result_text.get(line_start, line_end)
        self.play_song(line_text)

    def play_song(self, song_text):
        try:
            # Extract song details from the text
            song_info = song_text.split(". ")[1]  # Remove the number
            title, artist = song_info.split(" - ")
            
            # Find the song in the mood_songs dictionary
            for mood in self.mood_songs.values():
                for song in mood:
                    if song['title'] == title.strip() and song['artist'] == artist.strip():
                        webbrowser.open(song['url'])
                        return
                        
            self.result_text.insert(tk.END, "\n⚠️ Sorry, couldn't find the song's URL!\n")
        except Exception as e:
            self.result_text.insert(tk.END, f"\n⚠️ Error playing the song: {str(e)}\n")

def main():
    root = tk.Tk()
    app = MusicRecommenderGUI(root)
    root.mainloop()

if __name__ == "__main__":
    main()
