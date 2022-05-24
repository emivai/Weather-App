package com.example.weatherapp;

import static java.lang.String.valueOf;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.weatherapp.Utils.ForecastTimestamp;
import com.example.weatherapp.Utils.SportRating;
import com.example.weatherapp.databinding.FragmentFirstBinding;


import com.example.weatherapp.Utils.APIRequest;
import com.example.weatherapp.Utils.Cache;
import com.example.weatherapp.Utils.Forecast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment{

    private FragmentFirstBinding binding;
    private String city;
    private long day;
    private Date date;
    private ImageView weatherMainBlock;
    private boolean blockExpanded;
    private TextView weatherWindText;
    private TextView weatherPrecipationText;
    private TextView weatherVisibilityText;
    private View fragmentView;
    private String[] cities = {"abromiskes","acokavai","adakavas","adomyne","adutiskis","agluonai","agluonenai","aistiskiai","akademija","akademija-2","aklasis-ezeras","akmene","akmene-ii","akmene-iii","akmeniskes","akmenynai","akmenyne","aknystos","akstinai","akuotai","alanta","aleksandravas","aleksandravele","aleksandrija","aleksandrija-2","aleksandrija-3","alesninkai","alionys-i","alizava","alkiskiai","alksnenai","alksniupiai","alsedziai","alsiai","altoniskiai","aluona","alvitas","alytus","ambraziskiai","anavilis","anciskiai","anciunai","andrioniskis","andriskiai","andrusaiciai","anglininkai","ankstakiai","antakalnis","antakalnis-2","antakalnis-iii","antalge","antaliepte","antalksne","antanase","antanavas","antasava","antazave","antezeriai","anuziai","anyksciai","anziliai","apascia","apuole","arimaiciai","ariogala","arlaviskes","armalenai","armeniskiai","arnionys-i","arskainiai","arvydai","asminta","atkociai","atkociskes","augalai","augmenai","augustinava","aukstadvaris","aukstakalniai","aukstelkai","aukstelke","aukstikalniai","aukstkeliai","aukstuole","aukstupenai","aukstupiai","auksudys","auleliai","aunuvenai","avikilai","aviliai-ii","avizieniai","avizonys","azubaliai","azugiriai","azulauke","azuoline","azuolu-buda","azuozeriai","babriskes","babrungas","babtai","backininkai","backininkai-2","backiskiai","bagaslaviskis","bagaslaviskis-2","bagdononys","bagotoji","bagrenas","baibiai","baisogala","bajorai","bajorai-2","baksenai","balbieriskis","balceriskes","balciai","baleliai","balenos","balninkai","balsiai","balsiai-2","balsupiai","baltininkai","baltoji-voke","baltoji-voke-2","baltraitiske","baltrusaiciai","baltrusiai","baragine","bardauskai","bardinai","bardiskiai","bariunai","barklainiai-i","barskunai","barstyciai","bartkuskis","bartninkai","bartoniai","barzdai","barzdziai","batakiai","bategala","baubliai","bazilionai","bebrininkai","bebruliske","beciunai","beinorava","beizionys","bekupe","beloniskes","bendoriai","benininkai","berciunai","berciunai-2","berkinenai","bernatoniai","bernatoniai-2","bernotai","bertauciai","berteskiai","bertuziai","berzenai","berzininkai","berziskes","berzyne","betygala","bezdonys","bezdonys-2","bijotai","bijunai","bijunai-2","bijutiskis","bikavenai","biliakiemis","bilioniai","biliunai","birstonas","biruliskes","birzai","birzai-2","birzu-laukas","birzuvenai","bizieriai","blauzdziunai","blinstrubiskiai","bliudziai","bliuviskiai","bogdoniske","bokstai","boniskiai","bratoniskes","brazuole","bridai","brinkiskes","brukai","bubenai","bubiai","bubiai-2","bubleliai","bubliske","bubos","buciunai","buciunai-2","buda","budraiciai","budriai","budriai-2","budrikiai","budvieciai","bugeniai","buivydiskes","buivydziai-i","bukiskis","buknaiciai","bukonys","bukta","buktiskiai","buozenai","burbiskis","burgaiciai","burveliai","butenai","butenai-2","butkeliai","butkiske","butkiske-2","butniunai","butrimonys","butrimonys-2","byciai","bytlaukis","cedasai","ceikiniai","cekiske","cekoniskes","cekonys","cicinai","ciobiskis","cipariai","cirkliskis","ciudiskiai","ciurliai","ciuziakampis","ciziunai","cyckai","dabikine","dabuziai-i","dacijonai","dagiai","dagiai-2","dagiliskis","dailides","dailuciai","dainava","dainava-2","dainava-3","dainava-4","dainiai","dambrava","daniunai","dapkiskiai","dapkuniskiai","darginiai","darsuniskis","darzininkai","darzininkai-2","daubariai","daubenai","daubiskiai","daugai","daugailiai","daugedai","daugelaiciai","daugeliskiai","daugidonys","dauginciai","daugirdai","dauglaukis","daujenai","daujociai","daukantiskiai","daukniunai","dauksiai","dauksiai-2","dauksiai-3","daunoriske","debeikiai","dedeliskes","degaiciai","degesiai","deglenai","deguciai","deguciai-2","deguciai-3","deguciai-4","degutine","deltuva","dembava","dercekliai","didieji-bausiai","didieji-gulbinai","didieji-lygainiai","didieji-rusupiai","didieji-selviai","didkiemis","didsode","didvyciai","didvyriai","didysis-palukstis","didzialaukis","didziasalis","didziasalis-2","didzioji-riese","didziosios-kabiskes","didziosios-lapes","didziosios-selos","didziosios-zariskes","didziosios-zubiskes","didziuliai","dieveniskes","dievogala","dievogala-2","dievogala-3","diksiai","diliauskai","dimitriskes","dirkintai","dirvonenai","dobromisle","domeikava","dotnuva","dotnuva-2","dovainiai","dovainonys","dovilai","draseikiai","draseikiai-2","drasuciai","drasutaiciai","draudeliai","dreverna","drobuksciai","drobuksciai-2","druskininkai","druzai","dubenai","dubingiai","dubravai","dukstas","dukstos","dukurnonys","duobiskiai","duokiskis","dupulciai","duseikiai","dusetos","dusinenai","dusmenys","dvariskiai","dvylikiai","dziuginenai","egliskes","eiciai","eidintai","eigirdziai","eiksciai","eimuciai","einoraiciai","eisiskes","eitminiskes","elektrenai","elmininkai-i","emiliskes","endriejavas","eriskiai","erlenai","erzvilkas","ezerelis","ferma","gabriliava","gabsiai","gacioniai","gadunavas","gaideliai","gaidunai","gailaiciai","gailiai","gaizaiciai","gaizenai","gaizeneliai","gaiziunai","galgiai","gargzdai","garliava","garuckai","gasciunai","gatauciai","gaudikaiciai","gaure","gavaltuva","gedintes","gediskiai","gedrimai","gegrenai","geguziai","geguzine","geidziai","geidziunai","geisiskes","geisteriskiai","geisteriskiai-2","geleziai","gelezunai","gelgaudiskis","geluva","gelvonai","gerdziunai","gergzdeliai","germaniskis","geruliai","gervenai","gervenupis","gervine","gerviskes","gesalai","giedraiciai","giedraiciai-2","giedriai","giedruciai","gilaiciai","giliogiris","giluciai","gilutos","giluziai","gilvyciai","gineikiai","gineitiskes","ginenai","ginkunai","gintalai","gintaliske","gintauciai","giraite","girdeniai","girdiske","girdziai","girenai","girenai-2","girenai-3","girininkai","girininkai-ii","girionys","girkalnis","girminiai","girnikai","girsteitiskis","girsudai","girvainiai","gizai","glaudziai","glitiskes","gojus","gorainiai","gotlybiskiai","grabupiai","graziskiai","grazuciai","grendave","griciai","grigaiciai","grikiapeliai","grikieniai","grikpedziai","grimzdai","grimziai","grinaiciai","griniai","grinkiskis","gripiskes","griskabudis","grumbliai","gruslauke","gruzdiske","gruzdziai","gruziai","grybeliai","grybenai","gudai","gudeliai","gudeliai-2","gudeliai-3","gudeliai-4","gudeliai-5","gudeliai-6","gudeliai-7","gudiena","gudkaimis","gudzenka","gudziunai","gulbes","gulbinai","gulbinenai","gulbiniskiai","gulbiniskiai-2","guldynai","guostagalis","guragiai","gustonys","gyliai","gyvakarai","gyviskiai","gyvoliai","igliauka","igliskeliai","ignalina","ilgakiemis","ilgiziai-iii","ilguva","imbradas","ingavangis","inkakliai","inturke","ireniskiai","isdagai","islauzas","ismonys","isorai","izdonai","jadagoniai","jadvygiskes","jagelonys","jakaiciai","jakeliai","jakiskiai","jakutiskiai","janapole","janauciai","janciai","janciunai","jankai","jankunai","janukiskiai","janusava","jasiuliskis","jasiunai","jasiunai-2","jasiunai-3","jasonys","jatkanciai","jaucakiai","jauniunai","jauniunai-2","jiestrakis","jieznas","jiezno-kolonijos","joginiskiai","johampolis","jokubavas","jomantai","jonai","jonava","joniske","joniskelis","joniskelis-2","joniskis","joniskis-2","jonuciai","jonuciai-ii","jonuskai","josvainiai","jotainiai","jovaisiske","jovarai","jovariskes","jucaiciai","juciai","judrenai","judriai","juknaiciai","juknenai","jungenai","junkilai","juodaiciai","juodainiai","juodbaliai","juodbudis","juodeikiai","juodiskiai","juodpenai","juodpetriai","juodsiliai","juodupe","juodupenai","juostavieciai","juragiai","jurbarkai","jurbarkas","jurdaiciai","jure","jure-2","jurgelionys","jurgezeriai","jurginiskiai","juseviciai","juskaiciai","juskonys","juzintai","kabalda","kacergine","kadagiskiai","kadaiciai","kaimelis","kaimynai","kairenai","kairenai-2","kaireneliai","kairiai","kairiskiai","kaisiadorys","kakoniske","kalabariskes","kalesninkai","kalnas","kalnelio-grazioniai","kalnelis","kalnenai","kalnenai-2","kalnijos","kalniskiai","kalniskiai-2","kalniskiai-3","kalnujai","kalpokai","kaltanenai","kaltinenai","kalvarija","kalveliai","kalveliai-2","kalviai","kalviai-2","kalviskes","kamajai","kanapelka","kaniukai","kaniukai-2","kaniukai-3","kantauciai","kanteriskiai","kapciamiestis","kapenai","karaliai","karcemos","karciupis","kareivonys","kariotiskes","karkazai","karkaziske","karklenai","karklenai-2","karklenai-3","karkliniai","karlos","karmelava","karmelava-ii","karsakiskis","kartupiai","karvys","kasciukiskes","kasonys","kataucizna","katauskiai","katiliskiai","katinai","katleriai","kaubariskis","kaukolikai","kauksnujai","kaulakiai","kaunas","kaunas-aleksotas","kaunas-centras","kaunas-dainava","kaunas-eiguliai","kaunas-griciupis","kaunas-panemune","kaunas-petrasiunai","kaunas-sanciai","kaunas-silainiai","kaunas-vilijampole","kaunas-zaliakalnis","kaunatava","kausenai","kavarskas","kavoliskis","kazitiskis","kazlai-i","kazliskis","kazlu-ruda","kazokiskes","keblonys","kedainiai","kegai","keidziai","kelme","kena","kentriai","kepaliai","kerava","kernave","kernuves","kesciai","keturiai","keturiasdesimt-totoriu","keturkaimis","keturvalakiai","kiaukliai","kiauleikiai","kiaunoriai","kiduliai","kiemeliai","kiemeliai-2","kiemeliai-3","kiemenai","kijeliai","kiluciai","kimbartiske","kirdeikiai","kirdonys","kirkilai","kirkliai","kirmeliai","kirnaiciai","kirtimai","kisiniskiai","kivyliai","klaibunai","klaipeda","klaipeda-centras","klaipeda-gedminai","klaipeda-labrenciske","klaipeda-luize","klaipeda-lypkiai","klaipeda-melnrage","klaipeda-rumpiske","klaipeda-sendvaris","klaipeda-smelte","klaipeda-smiltyne","klaipeda-tauralaukis","klaipeda-zarde","klaisiai","klampuciai","klauseikiai","klausuciai","klausuciai-2","klausuciai-3","klebiskis","klibiai","klisiai","kloniai","kloniniai-mijaugonys","kloviniai","kluonaliai","kluoniskiai","klykiai","klykoliai","knieciai","kolainiai","kosiai-i","krakes","krakes-2","krakiai","krastai","kratiskiai","kraziai","kreipsiai","kreivalauziai","krekenava","kreslynai","kretingale","kretingsodis","kriaunos","krikliniai","krikstenai","krincinas","kriokiskiai","kriukai","kriukai-2","krokialaukis","krosna","kruonis","kruopiai","kubiliai","kubiliunai","kucgalys","kudirkos-naumiestis","kuigaliai","kukeciai","kukorai","kuktai","kuktiskes","kulai-i","kulautuva","kuliai","kuliai-2","kulokai","kulsenai","kultuvenai","kulupenai","kulva","kumelionys","kumpikai","kumzaiciai","kunigiskiai","kunigiskiai-2","kuniskiai","kuosenai","kuosine","kupiskis","kupreliskis","kupriai","kupriai-2","kuprioniskes","kuras","kurejai","kurkliai","kurkliai-ii","kurmaiciai","kurmaiciai-2","kursenai","kurtuvenai","kusliskiai","kutiskiai","kuziai","kveciai","kvedarna","kvesai","kvetkai","kvietiskis","kybartai","kybartai-2","kybeikiai","kyburiai","kyviskes","labanoras","labardziai","laibgaliai","laiciai","laiciai-2","laiciai-3","laikses","lailunai","laipuskiai","laiviai","laizuva","lakstuciai","lankaiciai","lankupiai","lapeles","lapes","lasai","lauciai","lauckaimis","laugalis","lauko-soda","lauksargiai","laukuva","laukzeme","laumenai","lavenai","lavoriskes","lazdenai","lazdijai","lazdininkai","leckava","leipalingis","leitgiriai","lekeciai","lekeciai-2","leliunai","leliunai-2","leliunai-3","lenas","lenkimai","lentvaris","leonpolis","lepsiai","leteniai","levaniskiai","levaniskiai-2","liausiai","liberiskis","liciunai","liepalotai","liepiai","lieplaukale","lieplauke","lieporai","lieporiai","liepynai","lindiniskes","lingailiai","linkaiciai","linkaiciai-2","linkauciai","linkaviciai","linkmenys","linksmakalnis","linksmuciai","lioliai","liubavas","liucinavas","liuciunai","liudvinavas","liudvinavas-2","liudyne","liukonys","liutonys","lizdeikiai","lomiai","lubiai","luknenai","luknes","lukonys","luksiai","luksiai-2","lukstai","lumpenai","luoke","luokesa","lupikai","lybiskiai","lyduokiai","lyduvenai","lyksilis","lyta","macikai","macionys","madziunai","maguciai","magunai","maironiai","maisiagala","maldenai","maldeniai","mamavys","manciusenai","maneikiai","manikunai","mankiskiai","mantviliskes","margininkai","marijampole","marijampolis","mastaiciai","masteikiai","mateikonys","matlaukys","matuizos","matuizos-2","mauruciai","mazaiciai","mazeikiai","mazeikoniai","mazenai","mazeniai","mazieji-budezeriai","mazieji-rusupiai","mazonai","medeikiai","medeniai","medikoniai","medingenai","mediniai-strevininkai","medininkai","medzionys","medziukai","megucioniai","meikstai","meilunai","mekiai","melekonys","mereslenai","merkine","meskalaukis","meskiai","meskuciai","meskuiciai","mezioneliai","micaiciai","mickunai","mielagenai","mieliunai","miezaiciai","mieziskiai","miezonys","miezonys-2","migonys","mikalauciskes","mikalauka","mikaliskes","mikasiunai","mikenai","mikniunai","mikoliskis","mikoliskis-2","mikytai","milasaiciai","milasaiciai-2","milgaudziai","milkunai","milkuskos","mimainiai","minaiciai","mindaugiai","mindunai","minupiai","miskalaukis","miskiniai","miskonys","mitkaiciai","mitkunai","mitriunai","mockai","mokolai","molainiai","moletai","moluvenai","mosedis","mostiskes","motiejunai","muniskiai","muro-strevininkai","musninkai","musteniai","mykoliskes","naciunai","naikiai","nairiai","naisiai","naiviai","nakiskiai","namisiai","narepai","narkunai","narsieciai","nartas","narteikiai","narvaisiai","narvydziai","nasrenai","natkiskiai","naudvaris","naudvaris-2","naugardiske","naujakiemis","naujamiestis","naujamiestis-2","naujarodziai","naujas-strunaitis","naujasis-daugeliskis","naujasis-lentvaris","naujasis-obelynas","naujasodis","naujasodis-2","naujasodis-3","naujasodis-4","naujasodis-5","naujatriobiai","naujieji-elmininkai","naujieji-muniskiai","naujiena","naujikai","naujoji-akmene","naujoji-uta","naujosios-kietaviskes","naukaimis","nausedai","nausedai-2","nausedziai","nausodis","navikai","nemaksciai","nemeiksciai","nemencine","nemencine-ii","nemezis","nemunaitis","nemunelio-radviliskis","nepreksta","nerimdaiciai","neringa","neringa-juodkrante","neringa-nida","neringa-pervalka","neringa-preila","netickampis","netoniai","nevarenai","neveronys","nevezis","nevociai","niauduva","noreikiai","noreikiskes","norgelai","norgelai-2","noriai","noriskiai","noriunai","norkaiciai","norkiske","notenai","nuotekai","obelaukiai","obeliai","obeliu-priemiestis","onuskis","opsrutai","ozkasviliai","oztakiai","pabaiskas","pabalve","pabare","paberze","pabirze","pabirzis","pabrade","paceriaukste-i","packenai","padainupys","padauguva","padovinys","padubysys","padustelis","padustis","padvariai","padvarionys","padvarionys-2","paeglesiai","paezere","paezeriai","paezeriai-2","paezeris","pagegiai","pageleziai","pagiriai","pagiriai-2","pagiriai-3","pagiriai-4","pagramantis","pagrybis","pagryniai","pagryzuvys","pagyne","paistrys","pajevonys","pajiesmeniai","pajiesys","pajotijys","pajuodupis","pajuostis","pajuralis","pajuris","pakalniai","pakalniskes","pakalniskes-2","pakalniskiai","pakalupis","pakape","pakene","pakerai","pakertai","paketuriai","pakevis","pakirsinys","pakonys","pakriauniai","pakruojis","pakumulsiai","pakuonis","palanga","palanga-sventoji","palevene","palevenele","paliepiai","paliepiai-2","paliuniskis","palnosai","palomene","palonai","paluknys","paluobiai","pamazupiai","pamusis","pamusis-2","pamusis-3","pandelys","pandelys-2","panemune","panemunelis","panemunelis-2","panemunis","paneveziukas","panevezys","panoteriai","panoviai","paparciai","paparciai-2","papieviai","papile","papilvis","papilys","papilys-2","papiskes","papiskes-2","papiskes-3","paprudziai","paprudziai-2","papusyne","papusynys","papyvesiai","paragaudis","paramotis","paraze","paringuvis","paroveja","parudaminys","pasakiai","pasaltuonys","paseimeniai","pasiause","pasilaiciai","pasile","pasile-2","pasilenai","paskynai","pastrevys","pasusvys","pasvalieciai","pasvalio-vienkiemiai","pasvalys","pasysiai","patamulselis","patasine","patasine-2","patilciai","patunkiskiai","pauliai","paupys","pavandene","pavartyciai","paverpenis","pavidaujys-i","pavirciuve","pavovere","pavydai","pazagieniai","pazerai","pazibai","pelaiciai","peleniskiai","pelkele","pelkes","pereksliai","pernarava","pervalkai","pervazninkai","petraliske","petrasiunai","petrasiunai-2","petreliai","petronys","petruliskes","piepaliai","piestuvenai","pietariai","pievenai","pikeliai","pikeliskes","piktakonys","piktuiziai","piktupenai","pikutiskes","pilis-i","piliuona","pilsudai","pilviskiai","pilypiskes","piniava","plaskiai","plateliai","platiniskes","platumai","plauciskiai","plentas","plikiai","plikiskiai","plinkses","ploksciai","plukiai","plunge","plutiskes","plyniai","pociunai","pociuneliai","polekele","porijai","poskonys","posupes","pozeciai","pozere","pragarele","prastavoniai","prauliai","pravieniskes-i","pravieniskes-ii","preiciunai","priekule","prienai","prienai-2","prienlaukys","prudiskes","prusaliai","pruseliai","prysmanciai","puciakalne","puckalaukis","pumpenai","pumpuciai","punzonys","puodkaliai","puodziai","puodziunai","puoke","pupiniai","puponys","purnuskes","purvenai","purvenai-2","purvininkai","purviniske","pusalotas","puskelniai","puskoniai","pustapedziai","pylimai","pypliai","pyragiai","racaliai","radeikiai","radikiai","radviliskis","radviloniai","ragaine","ragoziai","raguva","raguvele","raguviskiai","raikenai","rainiai","raisiai","raizgiai","rakonys","ramanava","ramonai","ramongaliai","ramoniskiai","ramuciai","ramuciai-2","ramuciai-3","ramygala","raseiniai","rastinenai","raubonys","raudenai","raudeniskiai","raudondvaris","raudondvaris-2","raudondvaris-3","raudondvaris-4","raudone","raudonenai","razaiteliai","raziskiai","recionys","reibiniai","reivyciai","reskutenai","ridelkalnis","riese","rietavas","rimkunai","rimse","ringaudai","ringuvenai","rinkunai","rinkunai-2","rinkuskiai","riogliskiai","ritine","rociskiai","rokai","rokiskis","romuciai","rotinenai","rotuliai","rubezius","rubikai","rubikiai","rubuliai","ruciunai","rudaiciai","rudamina","rudamina-2","rudausiai","rudiliai","rudiskiai","rudninkai","rudupiai","rukai","rukai-2","rukainiai","rukla","rukla-2","rumsiskes","ruponiai","rusne","rusonys","rutakiemis","ruteliai","rutkiskes","rutkiskiai","ruzgai","rykantai","rykstyne","rysininkas","ryskenai","sablauskiai","saboniai","sadunai","saduniskes","sakiai","sakiai-2","sakiai-3","sakiskes","sakuciai","sakuteliai","sakvietis","sakyna","salakas","salamiestis","salaperaugis","salcininkai","salcininkai-2","salcininkeliai","saldene","saldutiskis","salkininkai","salociai","salos","salos-2","salos-3","salote","samaniai","samylai","sangruda","santaka","sapnagiai","sapnagiai-2","sariai","sarkiai","sarnele","sartininkai","sasiai","sasnava","sateikiai","sateikiu-rudaiciai","sates","satijai","satkunai","satkunai-2","satraminiai","satrininkai","saugailiai","saugelaukis","sauginiai","saugos","saukava","saukenai","saukliai","saukotas","sauletekiai","sauliai","sauliai-i","sausine","sauslaukis","sauslaukis-2","saviciunai","seda","sedbarai","seduva","seirijai","selyne","semeliskes","sena-pasamine","senasis-tarpupis","seniava","senieji-trakai","senoji-ipiltis","senoji-radiske","sepeta","serdokai","seredzius","seriai","serksnenai","seskai","sestokai","sesuoleliai-i","sesuoliai","seta","siaudine","siaudiniai","siaudiniskiai","siauduva","siaulenai","siauliai","siauliai-2","siauliai-3","siauliskiai","sidabravas","sidabrines","sidagiai","siemuliai","sienlaukis","siesikai","sigutenai","siksniai","siksniai-2","silagalys","silai","silai-2","silai-3","silale","silavotas","silelis","silenai","silgaliai","silgaliai-2","siline","silute","siluva","simkaiciai","simnas","simonys","sintautai","siponys","siraiciai","sirutenai","sirvintos","sirvintos-2","sitkunai","siupyliai","skaidiskes","skaisciunai","skaistakaimis","skaisteriai","skaistgiriai","skaistgirys","skakai","skapiskis","skapiskis-2","skaudvile","skemai","skemiai","skersabaliai","skiemonys","skilvioniai","skirgailai","skirgiskes","skirlenai","skirmantiske","skirsnemune","skirzeme","skleriai","skogalis","skrebiskiai","skriaudziai","skudutiskis","skuodas","skuodiskiai","skurbutenai","skynimai","skynimai-2","slabada","slabada-2","slabadai","slavenai","slavikai","slienava","sliktine","slumpiai","smalininkai","smalininkai-2","smalvos","smelyne","smilgiai","smilgiai-2","smilgiai-3","smilgiai-4","smilgiai-5","smiltynai-i","smiltynai-ii","snipeliskis","sniukstai","sniuraiciai","soveniai","spanenai","spurganai","sriubiskiai","stabintiskes","staciunai","staciunai-2","stadviliai","stakiai","stakliskes","stalgenai","stanaiciai","staneliai","staniunai","stasiunai","stasiunai-2","staskuniskis","stasylos","statikai","steponkaimis","stirnenai","stonaiciai","stoniskiai","stragute","strazdiskes","strielciai","strigailiskis","striupai","struikai","struna","stubriai","stulgiai","stungiai","stupurai","subaciskes","subacius","subacius-2","sudava","sudeikiai","suderve","suginciai","sugintai","sujainiai","sujetai","suke","sukioniai","sukionys","sukuriai","sumskas","sungailiskiai","sunskai","surdegis","surviliskis","susninkai","sutkunai","suvainiskis","suvalkai","suvalkeliai","suviekas","suzionys","svedasai","sveicarai","sveicarija","sveksna","svencioneliai","svencionys","sventa","sventezeris","sventininkai","sventragis","sventupe","svetlica","svideniai","sviloniai","svirkos","svirnai-ii","svobiskis","syliai","tabariskes","talackoniai","tamosiai","tarnenai","tarprubeziai","tartokas","tarvydai","tarvydai-2","tauckunai","taujenai","taujenai-2","taujunai","taurage","tauragnai","taurai","taurupys","tausiunai","tautkaiciai","teiberiai","teleiciai","telsiai","teneniai","teresiskes","tetenai","tetirvinai","tickunai","tiltagaliai","tiltai","tirksliai","titkoniai","titoniai","toliejai","toliociai","toluciai","totorine","totorkiemis","trakai","trakenai","trakiskiai","trakiskiai-2","trakiskis","traksedis","traksedziai","traupis","trepai","tribonys","trilaukys","triobiskiai","triskoniai","troskuciai","troskunai","trudai","truikiai","truskava","tryskiai","tubausiai","tubines-i","tulnikiai","tulpiakiemis","turcinai","turgeliai","turmantas","tursuciai","tusciauliai","tverai","tverecius","tyruliai","tytuvenai","ubiske","udekai","udraliai","ukmerge","ukrinai","uliunai","uogaliai","uoginiai","uosininkai-i","upninkai","upyna","upyna-2","upyte","urvikiai","usenai","ustukiai","utena","uzbaliai","uzezere","uzgiriai","uzgiriai-2","uzkalniai","uzliedziai","uzliekne","uzliekniai","uzlieknis","uzpaliai","uzsieniai","uztilte","uzubaliai","uzugriovis","uzuguostis","uzukene","uzuneveziai","uzusaliai","uzusiliai","uzvente","uzventis","vabalai","vabaliai","vabalninkas","vadagiai","vadaktai","vadokliai","vadzgirys","vaickuniskes","vaiclaukis","vaidlonys","vaidotai","vaiguva","vaikutenai","vaineikiai","vainiunai","vainociai","vainutas","vaira","vaisvilciai-i","vaisvydava","vaitiekunai","vaitkunai","vaitkuskis","vaivadai","vaivadiskiai","valai","valai-2","valakeliai","valatkoniai","valaviciai","valciunai","valenai","valenciunai","valerava","valkininkai","valmantiskiai","valpainiai","vanagiai","vandziogala","varena","varine","varkaliai","varkaliskiai","varlaukis","varniai","varnupiai","varpuciai","varputenai","varsedziai","vartai","vartai-2","vasilenai","vaskai","vatusiai","veisiejai","veiveriai","veivirzenai","veliucionys","veliuona","velzelis","velzys","vembutai","vencloviskiai","venta","venta-2","vente","vepriai","verbiskes","verbunai","vereduva","veriskes","veriskiai","verksionys","verpena","versiai","versmininkai","vezaiciai","vezionys","vezionys-2","videniskiai","vidgiriai","vidiskes","vidiskiai","vidkiemis","vidsodis","vidukle","vidukle-2","vidutine","vieksnaliai","vieksniai","viesinteles","viesintos","viestovenai","viesvenai-i","viesvile","vievininkai","vievis","vijoliai","vijukai","vijurkai","vikonys","viktariskes","vileikiai","vileikiai-2","vileikiskiai","vilemai","vilionys","vilkaviskis","vilkija","vilkija-2","vilkiskes","vilkiskes-2","vilkiskiai","vilku-kampas","vilkupiai","vilkyciai","vilkyskiai","vilnius","vilnius-antakalnis","vilnius-balsiai","vilnius-fabijoniskes","vilnius-grigiskes","vilnius-justiniskes","vilnius-karoliniskes","vilnius-lazdynai","vilnius-naujamiestis","vilnius-naujininkai","vilnius-naujoji-vilnia","vilnius-paneriai","vilnius-pasilaiciai","vilnius-pilaite","vilnius-rasos","vilnius-santariskes","vilnius-senamiestis","vilnius-seskine","vilnius-snipiskes","vilnius-verkiai","vilnius-vilpede","vilnius-virsuliskes","vilnius-visoriai","vilnius-zirmunai","vilnius-zverynas","viluciai","vilunai","vindeikiai","vindeikiai-2","vingininkai","vinksnenai","virbalio-miesto-laukai","virbalis","virbaliskiai","virbaliskiai-2","virbaliunai","virgainiai","virksciai","virsuziglis","visaginas","visakio-ruda","visalauke-i","visbarai","vismantai","vistytis","vizanciai","vladikiskes","vokiskeliai","voniskiai","vosbutai","vosgeliai","vosiliskis","voskoniai","voskoniai-2","vosyliukai","voveriai","voveriai-2","voveriskiai","vyciai","vydeikiai","vydmantai","vysniunai","vytogala","vyzuoneles","vyzuonos","ylakiai","zadeikiai","zadeikoniai","zadvainai","zadvainiai","zadvarninkai","zadziunai","zagare","zagariai","zagarine","zaideliai","zaiginys","zaizdriai","zalavas","zalesa","zalpiai","zalvariai","zapyskis","zarasai","zarenai","zarenai-2","zasliai","zaugedai","zavisonys","zebertonys","zeimiai","zeimiai-2","zeimiai-3","zeimiai-4","zeimiai-5","zeimuvenai","zelkunai","zelsva","zelva","zelviai","zemaiciu-kalvarija","zemaiciu-naumiestis","zemaiteliai","zemaitkiemis","zemaitkiemis-2","zemaitkiemis-3","zemaitkiemis-4","zemale","zemoji-panemune","zerksciai","zibalai","zibartoniai","zibikai","zibuliai","zidikai","ziegzdriai","ziezmariai","zilpamusis","zindaiciai","ziniunai","ziobiskis","ziogaiciai","ziurai","ziuriai","zlibinai","zubiskes","zujunai","zukai","zutautai","zvainiai","zvelgaiciai","zvingiai","zvirbloniai","zvirgzdaiciai","zygaiciai","zygenai","zyniai"};
    private List<String> citiesList = Arrays.asList(cities);

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("bundleKey");
                // Do something with the result
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(result);
                    date = date1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                Date date2 = cal.getTime();
                long diff = date.getTime() - date2.getTime();
                day = (int) (diff / (1000 * 60 * 60 * 24));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        day = 0;
        date = new Date();
        weatherMainBlock = (ImageView)view.findViewById(R.id.weather_main_block);
        weatherWindText = (TextView)view.findViewById(R.id.text_wind);
        weatherVisibilityText = (TextView)view.findViewById(R.id.text_visibility);
        weatherPrecipationText = (TextView)view.findViewById(R.id.text_precipation);

        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("city", city);
                bundle.putLong("day", day);
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });*/

        binding.buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_settingsFragment);
            }
        });

        /**Today button sets day to today on click **/
        binding.buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                day = 0;
                date = new Date();
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
            }
        });

        /** Tommorow button sets day to tommorow on click **/
        binding.buttonToday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                day = 1;
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DAY_OF_MONTH, 1);
                date = c.getTime();
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
            }
        });

        /** "+" Button shows calendar on click **/
        binding.buttonToday3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new CalendarFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
            }
        });

        binding.buttonWeatherExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.someshape);
                //Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 100, true);


                //Button button = view.findViewById(R.id.button_settings);
                //Matrix blockMatrix = weatherMainBlock.getImageMatrix();
                /*int height = weatherMainBlock.getHeight();
                blockMatrix.setTranslate(1.2f, 1.0f);
                weatherMainBlock.setImageMatrix(blockMatrix);*/
                //int height = weatherMainBlock.getLayoutParams().height;
                //weatherMainBlock.getLayoutParams().height = 200;
                //Matrix imageMatrix = weatherMainBlock.getImageMatrix();
                //imageMatrix.setRotate(45);
                //weatherMainBlock.setImageMatrix(imageMatrix);

                if(blockExpanded){
                    ViewGroup.LayoutParams layoutParams = weatherMainBlock.getLayoutParams();
                    layoutParams.height -= 350;
                    weatherMainBlock.setLayoutParams(layoutParams);

                    weatherWindText.setVisibility(View.INVISIBLE);
                    weatherPrecipationText.setVisibility(View.INVISIBLE);
                    weatherVisibilityText.setVisibility(View.INVISIBLE);
                } else {
                    ViewGroup.LayoutParams layoutParams = weatherMainBlock.getLayoutParams();
                    layoutParams.height += 350;
                    weatherMainBlock.setLayoutParams(layoutParams);

                    weatherWindText.setVisibility(View.VISIBLE);
                    weatherPrecipationText.setVisibility(View.VISIBLE);
                    weatherVisibilityText.setVisibility(View.VISIBLE);
                }


                blockExpanded = !blockExpanded;
                //weatherMainBlock.setMinimumHeight(height + 100);
            }

        });

        /*SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);

        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d("REFRESH", "Refreshed");
                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );*/

        Forecast forecast = UpdateWeatherData();
        updateListView(view, forecast);

        /*
        // Load a bitmap from the drawable folder

Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.my_image);

// Resize the bitmap to 150x100 (width x height)

Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 100, true);

// Loads the resized Bitmap into an ImageView

ImageView image = findViewById(R.id.test_image);

image.setImageBitmap(bMapScaled);
         */

        //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment

        /*EditText edittext = (EditText) view.findViewById(R.id.edit_text_city);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String city = edittext.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("city", city);
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                    return true;
                } else {
                    city = edittext.getText().toString();
                }
                return false;
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Forecast UpdateWeatherData(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultValue = getResources().getString(R.string.selected_city_default_value);
        String city = sharedPref.getString(getString(R.string.selected_city), defaultValue);

        if(!citiesList.contains(city)){
            city = defaultValue;
        }
        APIRequest output = new APIRequest();
        String myUrl = String.format("https://jello-backend.herokuapp.com/forecasts/%s",city);   //String to place our result in
        //String myUrl = String.format("http://localhost:8080/forecasts/%s/long-term",city);   //String to place our result in
        String result = "<REPLACE>";   //Instantiate new instance of our class
        APIRequest getRequest = new APIRequest();   //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Forecast forecast = null;
        try {
            forecast = new Forecast(result, day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //final TextView cityTextView = (TextView) fragmentView.findViewById(R.id.forecast_city_name);
        //cityTextView.setText(forecast.city);

        //final TextView conditionTextView = (TextView) getView().findViewById(R.id.forecast_weather_condition);
        //conditionTextView.setText(forecast.getCurrentConditionCode());

        DateFormat formatter = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final TextView dayTextView = (TextView) getView().findViewById(R.id.text_day);
        String day1 = valueOf(day);
        dayTextView.setText( formatter.format(date).substring(0,3) + ", " + day1);

        final TextView airTemperatureTextView = (TextView) getView().findViewById(R.id.text_temperature);
        airTemperatureTextView.setText(valueOf(forecast.getCurrentAirTemperature()) + " °C");

        final TextView windSpeedTextView = (TextView) getView().findViewById(R.id.text_wind);
        windSpeedTextView.setText(valueOf("Wind: " + forecast.getCurrentWindSpeed()) + "(" + valueOf(forecast.getCurrentWindGust()) + ") m/s");

        final TextView precipitationTextView = (TextView) getView().findViewById(R.id.text_precipation);
        precipitationTextView.setText(valueOf("Precipitation: " + forecast.getCurrentPrecipitation()) + " mm/h");



        Log.d("data", forecast.getCurrentConditionCode());
        Log.d("data", valueOf(forecast.getCurrentAirTemperature()));
        Log.d("data", result);

        return forecast;
    }

    public void updateListView(View view, Forecast forecast){
        ListView listView = (ListView)view.findViewById(R.id.sportPresentationListView);
        ListView listView_moderate = (ListView)view.findViewById(R.id.sportModerateConditions);
        ListView listView_bad = (ListView)view.findViewById(R.id.sportBadConditions);
        List<String> sportsList = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        List<String> selectedSportList = new ArrayList<String>();

        List<String> moderateSportsList = new ArrayList<String>();
        List<String> badSportsList = new ArrayList<String>();

        for (int i = 0; i < sportsList.size(); ++i){
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            if (sharedPref.getBoolean(sportsList.get(i), false)){
                String rating = forecast.getCurrentTimestampSportRating(sportsList.get(i).toLowerCase());
                if(rating.equals("good")){
                    selectedSportList.add(sportsList.get(i));
                } else if(rating.equals("moderate")){
                    moderateSportsList.add(sportsList.get(i));
                } else if(rating.equals("bad")){
                    badSportsList.add(sportsList.get(i));
                }

            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, selectedSportList);
        listView.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapterModerate = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, moderateSportsList);
        listView_moderate.setAdapter(arrayAdapterModerate);

        ArrayAdapter arrayAdapterBad = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, badSportsList);
        listView_bad.setAdapter(arrayAdapterBad);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView textView = (CheckedTextView)view;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(textView.getText().toString(), textView.isChecked());
                editor.commit();
            }
        });*/
    }
    /*@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume()
    {
        super.onResume();
        UpdateWeatherData();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}