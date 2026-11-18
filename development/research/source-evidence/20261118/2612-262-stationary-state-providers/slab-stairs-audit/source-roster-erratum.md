# Source-roster erratum

The pre-claim frozen source-roster.md is retained unchanged at SHA-256 0a5e9ca4233749b24b9d9262e19db3db9ee5ee68b481406851a398f11418673b.

One copied member digest in its paired Java roster is incorrect due to transcription:
- net/minecraft/world/level/block/SlabBlock.java, 26.1.2: frozen roster says 883ac7561b118096d215127954eb243dcc60d10d4087bf256d895cf91a2ff854.
- Correct exact source-file and manifest digest: 883ac7561b118096d215def3e92bcc56336f4527d2a2587f2f4f9e948e162c8e.

The manifest itself is unchanged and verified at SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0. All other paired Java member digests in the frozen roster were rechecked against both the source file bytes and its source-manifest entry and match. The 26.2 SlabBlock digest also matches. The independent matrix content remains valid; this correction only repairs the roster's copied digest.

Encoding: UTF-8 without BOM, LF line endings.