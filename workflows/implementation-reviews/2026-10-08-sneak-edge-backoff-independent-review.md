# Independent review: sneak-edge backoff dispatch placement

**Ergebnis: ACCEPT.** Geprüft wurde der eingefrorene Autor-Commit `504f45a91fbbd5627ee9695bc2033c1ca02b69de` gegen Basis `d8f3956602da94bf0cf67753cc0a9f4665397729`; der Diff umfasst eine Datei mit zwei Einfügungen und einer Löschung. Es gibt **keine funktionalen Befunde**. Die Änderung verschiebt ausschließlich die bestehende `SneakEdgeBehavior`-Ersetzung in `PlayerMixin` von `RETURN` nach `HEAD` und ergänzt den Kommentar dazu.

## Befunde

Keine.

## Prüfung der angefragten Semantik

- **Moderne Methode und verworfene Arbeit:** Im exakten 26.2-`Player.java` (SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`, Manifest `ready/26.2/unobfuscated.sources.sha256`) führen `maybeBackOffFromEdge` und `canFallAtLeast` zusätzliche Kollisionstests aus. Die Tests lesen Blockzustände, Kollisionsformen, Entitäten und Weltgrenzen; sie schreiben in den geprüften Pfaden weder Player- noch Block-/Weltzustand. `BlockCollisions` hält den zuletzt abgefragten Chunk nur in einer Instanz des temporären Iterators. `Level.getChunkForCollisions` ruft `getChunk(..., ChunkStatus.FULL, false)` auf, also ohne Chunk-Laden/Generieren. Eine konkrete beobachtbare Nebenwirkung ist `Level.getEntities` → `Profiler.incrementCounter("getEntities")`: Bei historischen Profilen entfallen damit Zähler für die nun übersprungenen modernen Entity-Kollisionsabfragen. Das ist Diagnose-/Profiling-Arbeit, kein Bewegungs- oder Weltzustand. Ich fand in diesem Aufrufpfad keine weitere lesebedingte dauerhafte Cache- oder Weltänderung.
- **Historische Mathematik und Gates:** Die verifizierten Mojmap-Quellen 1.16.1 (`Player.java`, SHA-256 `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`) und 1.18.2 (`Player.java`, SHA-256 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`) zeigen dieselben X-, Z- und Diagonal-Probes: vollständige verschobene Bounding Box, Absenkung um `maxUpStep`, 0,05-Schritte in derselben Reihenfolge, unverändertes Y. Die 1.16.1-Gates sind SELF/PLAYER, `onGround` und `isStayingOnGroundSurface`; 1.18.2 ergänzt `!flying` und `isAboveGround`. Der gemeinsame Helper bildet genau die Schleifen ab; die versionierten Klassen behalten die jeweiligen Gates.
- **Operandbindung und Dispatch:** 26.2 `Entity.move` ruft `this.maybeBackOffFromEdge(delta, moverType)` direkt vor `this.collide(delta)` auf. Der Aufruf ist virtuell und erreicht damit `Player` für Spieler. Die am HEAD-Injektor anliegenden Werte sind somit der aktuelle Move-Vektor und der durch den vorhandenen `EntityMixin` gegebenenfalls angepasste `MoverType`. Der Handler übergibt dieselbe `Player`-Instanz, beide Argumente, den Wert von `isStayingOnGroundSurface()` sowie die per Spieler aufgelöste Probe-Distanz. Die exakte 26.2-Methode von `isStayingOnGroundSurface()` liest nur den Sneak-Zustand.
- **Profilauflösung und unveränderte Rückwege:** `MovementRuntime.find(type, player)` prüft den expliziten Spieler und liefert leer, wenn Emulation deaktiviert/modern ist oder kein passender Hook im Profil liegt. In diesen Fällen setzt der HEAD-Injektor keinen Rückgabewert und Vanilla läuft weiter; die vorhandene `canFallAtLeast`-Hook und der `maxUpStep`-Redirect bleiben im Vanilla-Aufruf erreichbar. Bei vorhandenem historischen `SneakEdgeBehavior` setzt `setReturnValue` den historischen Wert bereits am HEAD und überspringt damit die moderne Berechnung, deren Rückgabewert zuvor ohnehin ersetzt wurde. Die Distanz wird weiterhin separat aufgelöst; der bestehende `maxUpStep`-Redirect bleibt für nicht abgebrochene Vanilla-Aufrufe bestehen. Die registrierten Version-Implementierungen werden pro Mechanik ausgewählt und im Profil referenziert; die geänderte Injection führt keine neue Dispatch- oder Instanzlogik ein.

## Sechs Prüfwinkel

1. **Anforderung:** Erfüllt. Historische Profile ersetzen den verworfenen modernen Resultatpfad vor den Probes; nicht behandelte Profile behalten den Vanilla-Rückweg.
2. **Korrektheit und Randfälle:** Nichts gefunden. Gates, Eingabeoperanden, Y-Komponente, Distanzauflösung sowie deaktivierter/ohne-Handler-Pfad sind nachvollzogen.
3. **Fehlendes:** Nichts gefunden. Eine Repository-Suche bestätigt die bestehenden Hook-Typen, zwei historischen Handler, den separaten Probe-Hook und den Distanz-Redirect; für den geänderten Injector war keine weitere Aufrufstelle nachzuziehen.
4. **Konventionen und Duplikate:** Nichts gefunden. Der Patch fügt keine Bewegungslogik hinzu und nutzt weiterhin den gemeinsamen Helper.
5. **Berechtigungen und Sichtbarkeit:** Keine Berechtigungsänderung. Beobachtbar sinken nur die Profiling-Zähler für übersprungene moderne Entity-Probes unter behandelten historischen Profilen; das folgt aus dem gewünschten Wegfall der verworfenen Probe-Arbeit.
6. **Sicherheit:** Kein konkreter Angriffsweg oder sicherheitsrelevanter Eingabepfad durch die Änderung.

## Nicht verifiziert / nicht geprüft

- Laufzeitverhalten, insbesondere Profilwechsel gleichzeitig mit einem Movement-Aufruf, wurde statisch nicht experimentell verifiziert.
- Kein Build, Test, Decompile, Client, Server, TAS, Gym, Docker oder Push ausgeführt.
- Außerhalb des beschriebenen Aufrufpfads und der benannten Hook-/Runtime-Abhängigkeiten wurde keine vollständige Projektprüfung vorgenommen.

Der Bericht bindet sich an den Quellstand der Ready-Artefakte: `build/movement-campaign-2026-10-07/ready/{1.16.1/mojmap,1.18.2/mojmap,26.2/unobfuscated}`. Die drei `Player.java`-Hashes stimmen mit den jeweiligen `*.sources.sha256`-Manifestzeilen überein.

**Nächster Schritt:** Die Implementierungsverantwortlichen können den Placement-Change integrieren und die separat autorisierte Build-/Laufzeitvalidierung durchführen.

Reviewer: unabhängige statische Implementierungsprüfung, 2026-10-08.
