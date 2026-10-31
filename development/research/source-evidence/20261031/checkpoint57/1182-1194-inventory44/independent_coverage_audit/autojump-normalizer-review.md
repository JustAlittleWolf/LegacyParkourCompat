# Auto-jump normalization dependency closure

**Disposition: ACCEPT as an eligible movement response difference; confirms the pre-existing auto-jump source candidate and does not create a duplicate candidate.** The JOML dependency gap is closed using the exact 1.19.4 artifact published read-only at `.native-source/20261031/joml105-prep45/`.

The 1.19.3 `LocalPlayer.move` calls `updateAutoJump` after ordinary entity movement (`LocalPlayer.java:899–905`). There, `Mth.fastInvSqrt` normalizes the horizontal displacement, and the resulting value also feeds the prospective-distance expression `1.0F / $$12` used to extend the auto-jump probe (`LocalPlayer.java:931–954`). `Mth.fastInvSqrt(float)` uses the float-bit initial estimate and one Newton step (`Mth.java:495–501`).

In 1.19.4, the corresponding `updateAutoJump` calls `Mth.invSqrt` and uses it in the same normalization and prospective-distance roles (`LocalPlayer.java:881–934`). `Mth.invSqrt(float)` delegates to `org.joml.Math.invsqrt(float)` (`Mth.java:387–389`). The exact JOML 1.10.5 bytecode computes `1.0f / (float)Math.sqrt((double)value)` (`Math.javap.txt:675–684`), so this is a different float operation and result from the old bit-hack approximation. It changes the probe direction/scale used by the auto-jump collision decision when auto-jump is enabled. This is a local-player movement response and remains inside project scope.

The supplied jar SHA-256 `cac9f22f83a7aa33eebda73c16ff5261e3cb4911b6bafcf4c79ea486099d0c9a` matches the 1.19.4 `artifacts.sha256` row. The published `Math.javap.txt` digest is `0347840f3dc8529f88cf786fd61c2d823f7c02f4bc32fa71ea327e4c448e4a8e`, matching its publication metadata. This review uses static bytecode only; it executes neither the library nor the game.
