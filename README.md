# WII-Tanks-Facharbeit
Eine, unvollendete, Facharbeitskonzept über das Spiel [WII Tanks](https://nintendo.fandom.com/wiki/Tanks!)  
Ein Projekt, welches ich gerne wieder anfangen will, aber gut darstellt, wie man Daten darstellen kann.

# Trajectory Algorithmus
Die [Main Klasse](https://github.com/Conorsmine/WII-Tanks-Facharbeit/blob/main/src/main/java/com/Conorsmine/net/Main.java) beinhaltet einen selbstgeschriebenen Algorithmus,
welcher versucht eine Richtung auszumachen, um von Punkt A zu Punkt B ungehindert zu kommen.  
Der Algorithmus beruht auf folgender, rauer, Grafik:
![image](https://user-images.githubusercontent.com/79007923/220425429-61a32bb2-0043-4624-9ad3-c9d386b57b3a.png)
Jede dieser Farben beschreibt ein anderes Prinzip, die Richtung zum Punkt zu bestimmen.

## Standard (Blau)
Grundsätzlich wende ich einen Raycasting algorithmus an, der versucht innerhalb eines bestimmten Umfelds zu kommen.

## Instant-Hit (Rot)
Dies ist wohl der einfachste Fall:  
Er nimmt die Richtung zum Punkt und überprüft, ob dieser gehindert ist. Ist kein Hinderniss im Weg, kann diese Richtung sofort genutzt werden.

## Parallelity-Check (Grün)
Man erstellt zwischen Punkt A und Punkt B eine Gerade, welche zu den Punkten senkrecht liegt. Diese Gerade sucht einen Schnittpunkt mit einer Wand/reflektierendem Objekt,
in der Abbildung ist dieser Punkt `closestWallHit` genannt.  
Nun überprüft man, ob man von Punkt A und Punkt B, den `closestWallHit` ungehindert treffen kann, falls dies der Fall ist, nutze die Richtung zu diesem Punkt.



# Testresultate
Ich habe einige Versuche mit diesen [Algorithmen](#Trajectory-Algorithmus) angestellt und erhielt folgende Resultate:

## Timecomplexity
![image](https://user-images.githubusercontent.com/79007923/220427965-f988cd94-a6ce-4b36-8ba4-6c5e92e75a25.png)  
Man sieht sofort, dass es eine erhebliche Verbesserung gibt, wenn der [Instant-Hit](##Instant-Hit (Rot)) Algorithmus eingesetzt wird. Dies ist auch sinnvoll, da der 
[Parallelity-Check](##Parallelity-Check (Grün)) ein seltener und unzuverlässiger Fall ist.  
Und die **kleine** Verbesserung im Falle aller Algorithmen könnte auch ein Zufall sein, bzw. wird in einer echten Anwendung wohl keinen Einfluss haben.

## Failure over Time
![image](https://user-images.githubusercontent.com/79007923/220428912-ef686fdb-ab50-4e58-ac35-e8de999dd13c.png)  
Diese Grafik stellt dar, wie wahrscheinlich es ist, dass keine valide Richtung gefunden wird.  
Wichtig ist anzumerken, dass erneut der [Instant-Hit](##Instant-Hit (Rot)) Algorithmus eine erhebliche Verbesserung bietet. Die Fälle, in denen keine valide Richtung
gefunden wird sinkt stark. Der [Parallelity-Check](##Parallelity-Check (Grün)) Algorithmus hat nur einen moderaten Einfluss auf die Wahrscheinlichkeit.  
Dennoch ist das Anwenden aller Algorithmen von Vorteil, wenn man sich die, falsch beschriftete, Zeitaxe anschaut. Denn dann bemerkt man, dass das anwenden aller
Algorithmen dazu führt, dass invalide Möglichkeiten schneller ignoriert werden.  
Was mich aber überrascht hat, ist das keine "No Improvements" einen so rapiden Anstieg hat. Intuitiv wäre, dass es einen eher geradlinigen Anstieg hätte, da es
gleichmäßig in alle Richtungen einen Ray projiziert.
