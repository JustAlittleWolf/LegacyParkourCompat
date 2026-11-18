# Erratum — B04 audit manifest digest

Original `audit.md` SHA-256: `f885e5a742105dc43e25428e2ab7a5594d30c779e24de543c6106efe439a5f3` (reverified unchanged).

The 26.1.2 `unobfuscated.sources.sha256` digest was transcribed without its final `0`. Its independently recomputed SHA-256 is `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. The release's `unobfuscated.ready.json` carries the same digest. Direct validation of `net/minecraft/world/entity/Entity.java` also remains confirmed: its file SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` matches the exact entry in that manifest.

This corrects only the manifest digest transcription; it does not change the audit verdicts.
