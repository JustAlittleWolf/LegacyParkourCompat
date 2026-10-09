# Independent source review: D11–D13 key-mapping producers

- Review date: 2026-10-09
- Pair: Minecraft 1.21.4 (A) → 1.21.5 (B)
- Review base: immutable input-producer checkpoint `63720323792227435a8eadfe60c88f0b832585ea`
- Review scope: bounded D11 screen-transition release, D12 key-map construction/rebuild helpers, and D13 persisted key serialization path; source only.
- Verdicts: D11 **ACCEPT** for its bounded route; D12 **ACCEPT** for its three registry helpers; D13 **ACCEPT** for the save-only serialization route.
- Pair status remains partial. This report does not close D8 as a whole, adjudicate data-fix output types, or claim complete input-producer coverage.

## Publication and hash verification

The exact ready markers and manifests were re-read from the shared published Mojmap trees. The source hashes below were independently recomputed from the live files and matched their per-version source-manifest entries.

| Artifact | 1.21.4 | 1.21.5 |
|---|---|---|
| Ready marker SHA-256 | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` |
| Source manifest SHA-256 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` |
| Artifact manifest SHA-256 | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` |

Paired source file hashes:

| Exact source file | A SHA-256 | B SHA-256 |
|---|---|---|
| `net/minecraft/client/Minecraft.java` | `f4c9ba42f66a267ff01ff23f2bc9a8472d6f168effb1fc84314a613f730f94b1` | `95e4b37501d302cc1053951cbf6fdebe60d7ac4fdcbf6205cb51192d760e0e3e` |
| `net/minecraft/client/KeyMapping.java` | `d5475012d2849e7547e23d1584d5b099661a2cb1e60daebe6ec19cb28b779111` | same as A |
| `net/minecraft/client/ToggleKeyMapping.java` | `7830be5bce2146e9f8d69e79ca6f0f94617711b2f0f142ca826dd22ae23b4100` | same as A |
| `net/minecraft/client/Options.java` | `82c40cc5e82d2b43c49cea687d89d257e42285eb022760eb24991e93f75fb714` | `1db5025484669bd13727cfbc8bdfeef2663a35c962de63be258664db98d71522` |
| `net/minecraft/client/player/KeyboardInput.java` | `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |

## D11 — screen-transition key release

**Verdict: ACCEPT, bounded to the compared screen-transition route.**

A `Minecraft#setScreen(Screen)` lines 1030–1072 and B lines 1052–1093 first call `removed()` on the old non-null screen (or clear last input type if there was none), resolve null-screen substitutions, assign the resulting screen, and call `added()` when non-null. When the resolved screen is non-null, both then call `MouseHandler.releaseMouse()`, `KeyMapping.releaseAll()`, and screen initialization in that order. A additionally calls `BufferUploader.reset()` before the branch; it does not write mapping state. On a null result, both resume sound and grab the mouse. No key-state ordering delta was found.

A/B `KeyMapping#releaseAll()` lines 63–67 iterates `ALL.values()` and invokes `release()`; `release()` lines 116–119 clears click count before virtual `setDown(false)`. The identical `ToggleKeyMapping#setDown(boolean)` lines 15–23 calls its base writer on false only when its toggle supplier is false; for toggle-enabled mappings, a release does not clear the toggled state. This accurately describes the ordinary-versus-toggle result. A/B `KeyboardInput#tick()` reads the seven movement mappings at A lines 22–34/B lines 23–36, so the resulting predicates feed the existing local input sample.

This closes only the screen-transition caller and direct release state effect. It does not enumerate every caller of screen changes or other toggle reset writers.

## D12 — key mapping registration and map rebuild

**Verdict: ACCEPT, bounded to constructor registration, `setKey`, and `resetMapping`.**

The identical A/B `KeyMapping.java` hash covers the cited helper bodies. The typed constructor lines 89–97 creates the key from its type/code, stores it as both current and default key, adds the object to `ALL`, puts its current key in `MAP`, and records its category. The integer overload at lines 85–87 delegates using `KEYSYM`. `setKey` lines 129–131 changes only the current key field. `resetMapping` lines 77–83 clears `MAP` then repopulates it from each mapping in `ALL`. This verifies the claim that changing a mapping does not alter the event lookup table until a caller rebuilds it.

The consumer connection is supported by `KeyboardInput#tick()`: it reads the movement `KeyMapping.isDown()` predicates into `Input`; the D8 keyboard/mouse event writers resolve physical keys through `MAP`. This review accepts the helper behavior, not complete remapping lifecycle coverage.

## D13 — options key serialization

**Verdict: ACCEPT, bounded to the save-only route.**

A `Options#processOptions(FieldAccess)` lines 1205–1210 and B lines 1205–1210 iterate `keyMappings`, obtain `saveString()`, process `key_<mapping name>`, and call `setKey(InputConstants.getKey(value))` only when the adapter returns a different key string. In A/B `Options#save()` (A lines 1373–1452; B lines 1374–1454), the string `FieldAccess.process` writes the supplied key and value and returns that same value. Thus the save adapter serializes each current key identity without taking the key-change branch. The save calls `broadcastOptions()` after the write attempt in both versions. A/B `KeyMapping#saveString()` lines 170–172 returns the current key name.

B has an added `startedCleanly` option before the key loop. The fullscreen-resolution serialization differs between releases, but neither difference changes this key-binding path. The load adapter, controls-screen capture/reset, and map rebuild are separate routes reviewed in the next checkpoint.

## Dependency and limits

D11–D13 have no source-supported A/B movement-input delta in the bounded methods reviewed. Keep the typed value gap explicit: D15 records that A's load adapter converts any present tag through `getAsString()`, while B requires a `StringTag`; exact downstream `DataFixTypes.OPTIONS` transformations have not been closed. This review does not claim that the conditional non-string route is reachable or harmless.

The ordered bounded chain is key assignment or persisted current-key value → `setKey` where applicable → caller-triggered `resetMapping` → `MAP` event lookup → mapping down-state writer/release → `KeyboardInput#tick()` predicate sample. D14–D16 controls capture, reset, load, and screen-save edges require separate review.

No mod, wiki, or mixed coordinator evidence was inspected. No implementation, build, test, game/TAS/Gym/server runtime, Docker, push, or main-branch merge was performed.

---

## Independent source-review checkpoint: D14–D16

- Review checkpoint base: D11–D13 review commit `5e4c9aca`
- Verdicts: D14 **ACCEPT**, bounded to selected mouse/key capture plus map refresh; D15 **ACCEPT AS OPEN**, evidence is accurate but the typed post-data-fix boundary remains unresolved; D16 **ACCEPT**, bounded to the controls-screen Done → removal → options-save route.
- Pair remains partial; no full inventory or freeze is claimed.

Paired source hashes independently recomputed and matched the exact source-manifest entries:

| Exact source file | A SHA-256 | B SHA-256 |
|---|---|---|
| `net/minecraft/client/gui/screens/options/controls/KeyBindsScreen.java` | `37fb7af767757bc73759bdac2cd0e7577bed98d18d2566e172d16c381b5cf909` | same as A |
| `net/minecraft/client/gui/screens/options/controls/KeyBindsList.java` | `4b47787e3dda65051e3c4086f70cf7c9092db6e8c83b49b527ff9e03955dabc8` | same as A |
| `net/minecraft/client/gui/screens/options/OptionsSubScreen.java` | `af30ec4e20ebd4d09e4f7b68f8c95366e718f1fb76040c3f2680df7479ca805f` | same as A |
| `net/minecraft/nbt/CompoundTag.java` | `976935908129fca1b3d796431473b880a57ac70bcc28285d7b32c442776fbd8e` | `2e6527061af86adf42a7707b083ef83fab5a27e0b1bb62c6237451861ec63d65` |
| `net/minecraft/client/Options.java` | `82c40cc5e82d2b43c49cea687d89d257e42285eb022760eb24991e93f75fb714` | `1db5025484669bd13727cfbc8bdfeef2663a35c962de63be258664db98d71522` |
| `net/minecraft/client/Minecraft.java` | `f4c9ba42f66a267ff01ff23f2bc9a8472d6f168effb1fc84314a613f730f94b1` | `95e4b37501d302cc1053951cbf6fdebe60d7ac4fdcbf6205cb51192d760e0e3e` |

### D14 — controls-screen mouse and keyboard capture

**Verdict: ACCEPT, bounded to selected-mapping capture and immediate map refresh.**

A/B `KeyBindsScreen#mouseClicked(double,double,int)` lines 58–67 changes a key only when `selectedKey != null`: it assigns the mouse key, clears the selection, invokes `resetMappingAndUpdateButtons()`, and consumes the event; otherwise it delegates to the parent screen. A/B `keyPressed(int,int,int)` lines 70–85 uses the same selection gate; key code 256 assigns `UNKNOWN`, other keys use `InputConstants.getKey(keyCode, scanCode)`, then selection is cleared, the selection timestamp is set, the map/list refresh is called, and the event is consumed. Without a selected mapping, it delegates to the parent.

A/B `KeyBindsList#resetMappingAndUpdateButtons()` lines 54–57 calls `KeyMapping.resetMapping()` before refreshing entries. The same `KeyMapping.java` helper hash verified under D12 shows this refresh reconnects the newly assigned mapping key to `MAP`. This closes only capture after a mapping has already been selected; it does not include the earlier selection-start path or reset-all/load/save.

**Dispatch gates supporting D14:**

For the selected-control route, the consumer connection includes the paired event gates: A/B `KeyboardHandler#keyPress` dispatches to the active screen's `keyPressed` and returns when the screen consumes it; the selected-mapping handler returns true, so this capture does not fall through to a physical `KeyMapping.set` writer. A/B `MouseHandler#onPress` dispatches to `screen.mouseClicked` when there is no overlay and returns when consumed; its physical `KeyMapping.set` writer is gated by `screen == null && overlay == null`. This confirms capture assignments are distinct from held-key state writes. Exact paired source hashes, matched to the manifests: KeyboardHandler A `02476b9f11a44bdfb1a7336a4504848568e2f5bea7213c85ddd5afb181441c33`, B `5365cc6fa989061bfe0a84077fb3b8729d9741dc65f01412ea3dd4e3c983e299`; MouseHandler A `3c5a0747bfba9a43dc543eca13d58e42452c6e2b4046fdb1d7667763beb42dd9`, B `581910de8a6c0bbbf876f60cf0a05d5a9b50c7e0ff5009d637aaf39e59ea39a3`.

### D15 — options load and reset-all

**Verdict: ACCEPT AS OPEN.** The traced successful routes are supported, and the source-type dependency remains open exactly as recorded.

A/B `KeyBindsScreen#addFooter()` lines 38–49 builds reset-all by assigning every `options.keyMappings` entry its `defaultKey`, then calling `resetMappingAndUpdateButtons()`. The button does not directly clear each mapping's `isDown`; the screen-transition release behavior is separately covered by D11, including toggle-mode persistence. The Done button is also wired here and is followed through under D16.

A `Options#load()` lines 1226–1352 and B lines 1226–1353 return if the options file does not exist. Otherwise, both parse each accepted `key:value` line into a `CompoundTag` using `putString`, invoke `dataFix`, pass the result through `processOptions`, and call `KeyMapping.resetMapping()` only after processing succeeds. The outer catch logs an exception and skips later statements, including that map rebuild. The paired `processOptions` key loop updates a mapping with `setKey` only when the loaded key string differs from its current `saveString()`.

The helper difference remains material and unresolved: A's load adapter retrieves any present tag and invokes generic `getAsString()`; B explicitly accepts only `StringTag` and throws `IllegalStateException` for another present tag. The line parser itself writes strings, but `dataFix` runs before this accessor. No exact downstream `DataFixTypes.OPTIONS` transformation was audited here, so reachability of a post-fix non-string `key_*` tag is unknown. Such a tag reaches a distinct B failure gate; its exact A-side stringification and whether the transformed value changes a movement binding remain unadjudicated. Keep D15 open and do not claim equivalence.

The bounded `dataFix(CompoundTag)` wrappers (A lines 1362–1371, B lines 1363–1372) default an absent/invalid string version to zero and call `DataFixTypes.OPTIONS.updateToCurrentVersion`. That wrapper comparison does not establish the output types of the downstream versioned transformations.

### D16 — controls-screen Done and save ordering

**Verdict: ACCEPT, bounded to this controls-screen close route.**

A/B `KeyBindsScreen` extends `OptionsSubScreen` (line 16), and its Done button in `addFooter()` line 48 calls `onClose()`. The inherited `OptionsSubScreen#onClose()` lines 71–77 applies list changes if a list exists, then calls `Minecraft#setScreen(lastScreen)`. The inherited `removed()` lines 65–68 calls `minecraft.options.save()`; `KeyBindsScreen` does not override `removed()`. In both `Minecraft#setScreen` bodies, the current non-null screen's `removed()` runs before the target screen is assigned. Therefore this close path reaches the D13 key serialization before any later non-null-target `releaseAll()` in that transition. The route is identical in A/B. This closes only the controls-screen Done-to-save trigger, not other save callers or the D15 load transformation dependency.

## Review conclusion and limits

The producer/consumer edges in this assignment are source-supported in bounded order: selected mapping key write → immediate `MAP` rebuild for capture/reset; persisted key processing → rebuild only on successful completion; controls-screen Done → `removed()` → options save; mapping state writers/releases → `KeyboardInput#tick()` predicates. The string-type result of versioned option data fixing remains an explicit open dependency. D8 as a whole, D1–D6, and full-pair audit/freeze remain open; overall pair status is partial.

No mod or wiki sources were opened. No source run, shared publication, or finding was edited. No build, test, game/TAS/Gym/server runtime, Docker operation, push, or main-branch merge was performed.