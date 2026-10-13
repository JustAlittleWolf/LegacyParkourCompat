# Auto-jump collision probe uses a different inverse-square-root calculation

1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 900-996.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 891-987.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/util/Mth.java`, fastInvSqrt 485-491.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/util/Mth.java`, invSqrt 387-389.

Difference: For the same float squared length, 1.18.2's `fastInvSqrt` approximate result and 1.19.4's JOML inverse-square-root result are produced by different binary32 paths. For example, at `$$6 = 0.01F`, 1.18.2's one-step approximation returns `9.982522F` (`0x411fb869`), while 1.19.4's JOML helper returns `10.0F` (`0x41200000`). `updateAutoJump` multiplies its horizontal vector by that result, then uses the normalized vector in the forward-dot cutoff, scan endpoint, and side-ray endpoints. The remainder of the inspected scan and trigger predicates is unchanged apart from floor-equivalent block-position construction.

Movement meaning: The vector supplied to the auto-jump facing and collision probes can differ numerically, which can shift their endpoints or boundary comparisons for inputs and collision geometry close to a probe threshold. Source confirms the numeric path change; this audit did not calculate a concrete block layout whose trigger outcome changes and did not run a trajectory.
