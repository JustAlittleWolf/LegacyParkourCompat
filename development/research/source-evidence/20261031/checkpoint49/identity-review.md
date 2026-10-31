# Independent review: player-owned state identity

Verdict: **ACCEPT**

Reviewed integration commit `95aa5cde8806f7129b1520827381c3ffdc701c5f` in `.native-integrations/player-state-identity48`.

## Findings

- `WeakIdentityState` uses reference comparison (`owner.get() == candidate`) for lookup, replacement, and clearing. A new `Player` instance with the same mutable entity ID cannot retrieve, overwrite, or clear the previous instance's state. Mutating an existing instance's ID does not change ownership. This matches the 26.2 `Entity.equals` / `hashCode` implementation at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java:421-426`, which compares and hashes by `getId()`.
- The helper stores the owner only through `WeakReference`; its supplier is not retained. The `State`, `Effect`, and `SprintTimer` values in the three call sites have no `Player` field or owner backreference, so the value does not keep the owner alive.
- The helper's single-owner contract is consistent with these runtime call paths: packet capture, effect ticking and reads explicitly require the current `Minecraft` player; sprint hooks are attached to `LocalPlayer`. It does not promise preservation for interleaved arbitrary owners, which is outside the stated contract.
- The two jump-effect receivers preserve their prior operation flow: `getOrCreate` replaces `computeIfAbsent`, `get` replaces map lookup, and identity-scoped `clear` replaces removal. The `duration == -1` native-session path still clears the emulated effect and remains native until matching removal; amplifier/duration update rules, expiry ticking and native fallback are unchanged.
- `SprintDuration` still initializes to 600 or 0 on sprint-state changes, decrements only positive values once per tick, and calls `setSprinting(false)` exactly when the counter reaches zero. Replacing the boxed map value with a mutable timer holder does not alter that schedule.

No defects found in the requested scope. This is a static review only; no tests, builds, game launches, or decompilation were performed.
