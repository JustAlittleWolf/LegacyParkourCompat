# Mounted double-tap sprint ground gate

## Accepted source record

`comparison.md` is the original independently reviewed source comparison. Its SHA-256 is `7d7b3a7e1018c11c79f9a1fb6bdcb9c0598e8cc6b3a499d3c9b74e16c8fa0725`. `verdict.md` is the original independent audit verdict. Its SHA-256 is `23df43804001c82b0f53c77c65365042bc49dc90ca7bec55411a74c3cb14412c`. The copied file bytes match both supplied identities.

The accepted gate is bounded to the inspected Mojmap 1.19.3 and 1.19.4 `net.minecraft.client.player.LocalPlayer.aiStep()` paths. In 1.19.3, double-tap admission accepts player ground, underwater, or a grounded passenger vehicle. In 1.19.4, mounted admission selects vehicle ground instead of player ground, then allows underwater. The held sprint-key path remains a separate route without a ground gate. The comparison also records the unchanged ground predicate through 1.19.2; implementation preserves that behavior for selections from 1.18.2 and returns to native behavior below that inspected floor.

The 1.19.4 vehicle eligibility predicate remains delegated to the existing product hook and native method. The accepted Camel witness requires the `UPDATE_1_20` feature flag and is not required by this implementation. The dismount-to-jump claim remains conditional in the audit verdict and is not implemented or relied on here.

## Product association and verification

- Product change commit: `ecc2eb923fcd6432db3e01554f8e0e6fe66064eb`.
- Combined product-history commit: `21cd9c100b815520aa1ddc2cdfe1e4ba9b5a2314`, based on the then-current main `e1fbd2e02c88dab65b01d12ae39d5a443fc01a5e` with no merge conflicts. The prior passenger-sync change `c96889fb25580fc6eaff6a15b7953f7e76e0eeb1` is in its ancestry.
- Build tree identity: combined commit `21cd9c100b815520aa1ddc2cdfe1e4ba9b5a2314`.
- Windows build: `.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain` — `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. Every Gradle `Test` task was disabled by the init script and tests were excluded with `-x test`.
- Static evidence check: `git diff --check` on the evidence-only change.

The product implementation gates only the 26.2 `LocalPlayer.aiStep()` double-tap timer write and completion call. The separate held-key `setSprinting` call remains native. Runtime parity was not exercised.
