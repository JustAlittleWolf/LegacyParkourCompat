# Draft hash binding correction

The exact `sleep-pitch-source/comparison.md` reviewed in this audit was hashed before review and recomputed afterward. Both checks produced SHA-256 `ff5165c4ffe59dd62e521bb66f97636617224049d5a1a305d7f71b2ee895d867`; the file identity is unchanged.

The immutable `verdict.md` SHA-256 is `aa45b36d8a07045181076fa8c729246b76fccc4ff42c7dadac4fe59a6ef800ba`. Its input-binding line contains a one-nibble transcription error (`...bb66b976...`); the correct digest is `...bb66f976...`. This note binds the existing verdict to the verified draft hash without modifying the verdict.