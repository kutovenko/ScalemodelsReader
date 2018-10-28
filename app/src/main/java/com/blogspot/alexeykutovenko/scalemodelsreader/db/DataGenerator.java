package com.blogspot.alexeykutovenko.scalemodelsreader.db;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.converters.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataGenerator {
//Id - i

    private static final String[] ONLINE_ID = new String[]{
            "13167",
            "13164",
            "13166",
            "13165",
            "13163",
            "13167",
            "13164",
            "13166",
            "13165",
            "13163",
            "13167",
            "13164",
            "13166",
            "13165",
            "13163",
            "13167",
            "13164",
            "13166",
            "13165",
            "13163",
            "13167",
            "13164",
            "13166",
            "13165",
            "13163"
    };

    private static final String[] TITLE_NAME = new String[]{
            "Анонс Special Hobby 1/72 Dassault Mirage IIIS/IIIRS/5BA/5F - 3D-render",
            "Совместная постройка (ГБ) – Самолет Lockheed P-38 Lightning",
            "Анонс АIMS models 1/48 Ju 88C-6",
            "Анонс конкурса Group Build: Подводные лодки",
            "Звезда 1/144 AH-64 Apache HECU",
            "Анонс Special Hobby 1/72 Dassault Mirage IIIS/IIIRS/5BA/5F - 3D-render",
            "Совместная постройка (ГБ) – Самолет Lockheed P-38 Lightning",
            "Анонс АIMS models 1/48 Ju 88C-6",
            "Анонс конкурса Group Build: Подводные лодки",
            "Звезда 1/144 AH-64 Apache HECU",
            "Анонс Special Hobby 1/72 Dassault Mirage IIIS/IIIRS/5BA/5F - 3D-render",
            "Совместная постройка (ГБ) – Самолет Lockheed P-38 Lightning",
            "Анонс АIMS models 1/48 Ju 88C-6",
            "Анонс конкурса Group Build: Подводные лодки",
            "Звезда 1/144 AH-64 Apache HECU",
            "Анонс Special Hobby 1/72 Dassault Mirage IIIS/IIIRS/5BA/5F - 3D-render",
            "Совместная постройка (ГБ) – Самолет Lockheed P-38 Lightning",
            "Анонс АIMS models 1/48 Ju 88C-6",
            "Анонс конкурса Group Build: Подводные лодки",
            "Звезда 1/144 AH-64 Apache HECU",
            "Анонс Special Hobby 1/72 Dassault Mirage IIIS/IIIRS/5BA/5F - 3D-render",
            "Совместная постройка (ГБ) – Самолет Lockheed P-38 Lightning",
            "Анонс АIMS models 1/48 Ju 88C-6",
            "Анонс конкурса Group Build: Подводные лодки",
            "Звезда 1/144 AH-64 Apache HECU"
    };

    private static final String[] AUTHOR = new String[]{
            "Alexey", "Sergey", "Misha", "Sasha", "SilverGhost",
            "Alexey", "Sergey", "Misha", "Sasha", "SilverGhost",
            "Alexey", "Sergey", "Misha", "Sasha", "SilverGhost",
            "Alexey", "Sergey", "Misha", "Sasha", "SilverGhost",
            "Alexey", "Sergey", "Misha", "Sasha", "SilverGhost"
    };

    private static final String[] THUMBNAIL = new String[]{
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537391402_3.jpg",
            "http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"
    };

    private static final String[] images1 = new String[]{"http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"};
    private static final String[] images2 = new String[]{"http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"};
    private static final String[] images3 = new String[]{"http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"};
    private static final String[] images4 = new String[]{"http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"};
    private static final String[] images5 = new String[]{"http://scalemodels.ru/images/2018/09/thumbs/1537360897_P9020108.JPG",
            "http://scalemodels.ru/images/2018/09/thumbs/1537379789_01_DSC02669.jpg"};

    private static final String[] IMAGES_URL = new String[]{
            MyAppHelper.convertArrayToString(images1),
            MyAppHelper.convertArrayToString(images2),
            MyAppHelper.convertArrayToString(images3),
            MyAppHelper.convertArrayToString(images4),
            MyAppHelper.convertArrayToString(images5),
            MyAppHelper.convertArrayToString(images1),
            MyAppHelper.convertArrayToString(images2),
            MyAppHelper.convertArrayToString(images3),
            MyAppHelper.convertArrayToString(images4),
            MyAppHelper.convertArrayToString(images5),
            MyAppHelper.convertArrayToString(images1),
            MyAppHelper.convertArrayToString(images2),
            MyAppHelper.convertArrayToString(images3),
            MyAppHelper.convertArrayToString(images4),
            MyAppHelper.convertArrayToString(images5),
            MyAppHelper.convertArrayToString(images1),
            MyAppHelper.convertArrayToString(images2),
            MyAppHelper.convertArrayToString(images3),
            MyAppHelper.convertArrayToString(images4),
            MyAppHelper.convertArrayToString(images5),
            MyAppHelper.convertArrayToString(images1),
            MyAppHelper.convertArrayToString(images2),
            MyAppHelper.convertArrayToString(images3),
            MyAppHelper.convertArrayToString(images4),
            MyAppHelper.convertArrayToString(images5)
    };

    private static final String[] ORIGINAL_URL = new String[]{
            "http://scalemodels.ru/modules/myarticles/print.php?storyid=12459",
            "http://scalemodels.ru/news/13158-anons-Special-Hobby-1-72-Dassault-Mirage-IIIS-IIIRS-5BA-5F---3D-render.html",
            "http://scalemodels.ru/news/13157-sovmestnaja-postrojjka-gb--samolet-Lockheed-P-38-Lightning.html",
            "http://scalemodels.ru/news/13155-anons-aIMS-models-1-48-Ju-88C-6.html",
            "http://scalemodels.ru/news/13156-anons-konkursa-Group-Build--podvodnye-lodki.html",
            "http://scalemodels.ru/news/13159-Walkaround-su-15-b-n-01-aviabaza-dorokhovo-bezheck-5-tverskaja-oblast-rossija.html",
            "http://scalemodels.ru/news/13158-anons-Special-Hobby-1-72-Dassault-Mirage-IIIS-IIIRS-5BA-5F---3D-render.html",
            "http://scalemodels.ru/news/13157-sovmestnaja-postrojjka-gb--samolet-Lockheed-P-38-Lightning.html",
            "http://scalemodels.ru/news/13155-anons-aIMS-models-1-48-Ju-88C-6.html",
            "http://scalemodels.ru/news/13156-anons-konkursa-Group-Build--podvodnye-lodki.html",
            "http://scalemodels.ru/news/13159-Walkaround-su-15-b-n-01-aviabaza-dorokhovo-bezheck-5-tverskaja-oblast-rossija.html",
            "http://scalemodels.ru/news/13158-anons-Special-Hobby-1-72-Dassault-Mirage-IIIS-IIIRS-5BA-5F---3D-render.html",
            "http://scalemodels.ru/news/13157-sovmestnaja-postrojjka-gb--samolet-Lockheed-P-38-Lightning.html",
            "http://scalemodels.ru/news/13155-anons-aIMS-models-1-48-Ju-88C-6.html",
            "http://scalemodels.ru/news/13156-anons-konkursa-Group-Build--podvodnye-lodki.html",
            "http://scalemodels.ru/news/13159-Walkaround-su-15-b-n-01-aviabaza-dorokhovo-bezheck-5-tverskaja-oblast-rossija.html",
            "http://scalemodels.ru/news/13158-anons-Special-Hobby-1-72-Dassault-Mirage-IIIS-IIIRS-5BA-5F---3D-render.html",
            "http://scalemodels.ru/news/13157-sovmestnaja-postrojjka-gb--samolet-Lockheed-P-38-Lightning.html",
            "http://scalemodels.ru/news/13155-anons-aIMS-models-1-48-Ju-88C-6.html",
            "http://scalemodels.ru/news/13156-anons-konkursa-Group-Build--podvodnye-lodki.html",
            "http://scalemodels.ru/news/13159-Walkaround-su-15-b-n-01-aviabaza-dorokhovo-bezheck-5-tverskaja-oblast-rossija.html",
            "http://scalemodels.ru/news/13158-anons-Special-Hobby-1-72-Dassault-Mirage-IIIS-IIIRS-5BA-5F---3D-render.html",
            "http://scalemodels.ru/news/13157-sovmestnaja-postrojjka-gb--samolet-Lockheed-P-38-Lightning.html",
            "http://scalemodels.ru/news/13155-anons-aIMS-models-1-48-Ju-88C-6.html",
            "http://scalemodels.ru/news/13156-anons-konkursa-Group-Build--podvodnye-lodki.html"
    };

    private static final Date[] DATE = new Date[]{
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737)

    };

    private static final int[] CATEGORY_ID = new int[]{
            31,
            42,
            17,
            20,
            19,
            31,
            42,
            17,
            20,
            19,
            31,
            42,
            17,
            20,
            19,
            31,
            42,
            17,
            20,
            19,
            31,
            42,
            17,
            20,
            19
    };

    private static final String[] CATEGORY_NAME = new String[]{
            "Amarket-Model.ru",
            "modelist.ru",
            "1/32",
            "1/72",
            "1/35",
            "Amarket-Model.ru",
            "modelist.ru",
            "1/32",
            "1/72",
            "1/35",
            "Amarket-Model.ru",
            "modelist.ru",
            "1/32",
            "1/72",
            "1/35",
            "Amarket-Model.ru",
            "modelist.ru",
            "1/32",
            "1/72",
            "1/35",
            "Amarket-Model.ru",
            "modelist.ru",
            "1/32",
            "1/72",
            "1/35"
    };

    private static final Date[] LAST_UPDATED = new Date[]{
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538159737),
            DataConverters.toDate((long)1538118060),
            DataConverters.toDate((long)1538114460),
            DataConverters.toDate((long)1537979947),
            DataConverters.toDate((long)1538159737)
    };

    private static final String[] DESCRIPTION = new String[]{
            "Еще одна модель от Revell в 1/72. Изкоробка, добавил только антенны и поручни. Пытался сделать мигалку из прозрачного литника, но результат не устроил, и мигалка была оторвана)",
            "Предисловие. Планов собирать этого клопа в общем-то не было, но в параллель к основному проекту в качестве упражнения решил склеить, главным образом из-за декали, которая оказалась весьма плохого качества.",
            "Ну что, как и было обещано - между крупной деколью и деколью поменьше перерывчик небольшой. Выдана на гора деколь 72-067 Сухой Су-34. ",
            "Сегодня Андрей \"SilverGhost\" Кузнецов и Иван \"VMMaster\" Мешков представляют огромный совместный фотообзор первой серийной модификации истребителя-перехватчика Сухой Су-15 (НАТО - Flagon-A) б/н 01, серийный № 09-1, установленного на въезде в военный городок бывшей авиабазы Дорохово, Бежецк-5, Тверская область, Россия. Самолет выпущен примерно в 1968-1969 г., летал в составе 611 ИАП. При перевооружении полка на Су-27 в середине 1980-х годах, установлен на въезде в военный городок. Именной борт в честь Героя Великой Отечественной войны Николая Даниленко. 611 ИАП был первым полком в СССР, который проводил войсковые испытания, освоение и принял на вооружение Су-15. Полк с ними заступил на боевое дежурство 30 октября 1968 г. На параде в Домодедово в 1967 г. летали Су-15 именно этого полка. На текущий момент, это наиболее полный фотообзор в сети ранней модификации Су-15. Самолет также полностью отснят сверху. Перейти к фотообзору",
            "Правила Совместной постройки (ГБ) – Самолет Lockheed P-38.Принимая во внимание приближающийся юбилей первого полета самолета P-38 и постоянный интерес к данному самолету, проводим данный конкурс. ",
            "Еще одна модель от Revell в 1/72. Изкоробка, добавил только антенны и поручни. Пытался сделать мигалку из прозрачного литника, но результат не устроил, и мигалка была оторвана)",
            "Предисловие. Планов собирать этого клопа в общем-то не было, но в параллель к основному проекту в качестве упражнения решил склеить, главным образом из-за декали, которая оказалась весьма плохого качества.",
            "Ну что, как и было обещано - между крупной деколью и деколью поменьше перерывчик небольшой. Выдана на гора деколь 72-067 Сухой Су-34. ",
            "Сегодня Андрей \"SilverGhost\" Кузнецов и Иван \"VMMaster\" Мешков представляют огромный совместный фотообзор первой серийной модификации истребителя-перехватчика Сухой Су-15 (НАТО - Flagon-A) б/н 01, серийный № 09-1, установленного на въезде в военный городок бывшей авиабазы Дорохово, Бежецк-5, Тверская область, Россия. Самолет выпущен примерно в 1968-1969 г., летал в составе 611 ИАП. При перевооружении полка на Су-27 в середине 1980-х годах, установлен на въезде в военный городок. Именной борт в честь Героя Великой Отечественной войны Николая Даниленко. 611 ИАП был первым полком в СССР, который проводил войсковые испытания, освоение и принял на вооружение Су-15. Полк с ними заступил на боевое дежурство 30 октября 1968 г. На параде в Домодедово в 1967 г. летали Су-15 именно этого полка. На текущий момент, это наиболее полный фотообзор в сети ранней модификации Су-15. Самолет также полностью отснят сверху. Перейти к фотообзору",
            "Правила Совместной постройки (ГБ) – Самолет Lockheed P-38.Принимая во внимание приближающийся юбилей первого полета самолета P-38 и постоянный интерес к данному самолету, проводим данный конкурс. ",
            "Еще одна модель от Revell в 1/72. Изкоробка, добавил только антенны и поручни. Пытался сделать мигалку из прозрачного литника, но результат не устроил, и мигалка была оторвана)",
            "Предисловие. Планов собирать этого клопа в общем-то не было, но в параллель к основному проекту в качестве упражнения решил склеить, главным образом из-за декали, которая оказалась весьма плохого качества.",
            "Ну что, как и было обещано - между крупной деколью и деколью поменьше перерывчик небольшой. Выдана на гора деколь 72-067 Сухой Су-34. ",
            "Сегодня Андрей \"SilverGhost\" Кузнецов и Иван \"VMMaster\" Мешков представляют огромный совместный фотообзор первой серийной модификации истребителя-перехватчика Сухой Су-15 (НАТО - Flagon-A) б/н 01, серийный № 09-1, установленного на въезде в военный городок бывшей авиабазы Дорохово, Бежецк-5, Тверская область, Россия. Самолет выпущен примерно в 1968-1969 г., летал в составе 611 ИАП. При перевооружении полка на Су-27 в середине 1980-х годах, установлен на въезде в военный городок. Именной борт в честь Героя Великой Отечественной войны Николая Даниленко. 611 ИАП был первым полком в СССР, который проводил войсковые испытания, освоение и принял на вооружение Су-15. Полк с ними заступил на боевое дежурство 30 октября 1968 г. На параде в Домодедово в 1967 г. летали Су-15 именно этого полка. На текущий момент, это наиболее полный фотообзор в сети ранней модификации Су-15. Самолет также полностью отснят сверху. Перейти к фотообзору",
            "Правила Совместной постройки (ГБ) – Самолет Lockheed P-38.Принимая во внимание приближающийся юбилей первого полета самолета P-38 и постоянный интерес к данному самолету, проводим данный конкурс. ",
            "Еще одна модель от Revell в 1/72. Изкоробка, добавил только антенны и поручни. Пытался сделать мигалку из прозрачного литника, но результат не устроил, и мигалка была оторвана)",
            "Предисловие. Планов собирать этого клопа в общем-то не было, но в параллель к основному проекту в качестве упражнения решил склеить, главным образом из-за декали, которая оказалась весьма плохого качества.",
            "Ну что, как и было обещано - между крупной деколью и деколью поменьше перерывчик небольшой. Выдана на гора деколь 72-067 Сухой Су-34. ",
            "Сегодня Андрей \"SilverGhost\" Кузнецов и Иван \"VMMaster\" Мешков представляют огромный совместный фотообзор первой серийной модификации истребителя-перехватчика Сухой Су-15 (НАТО - Flagon-A) б/н 01, серийный № 09-1, установленного на въезде в военный городок бывшей авиабазы Дорохово, Бежецк-5, Тверская область, Россия. Самолет выпущен примерно в 1968-1969 г., летал в составе 611 ИАП. При перевооружении полка на Су-27 в середине 1980-х годах, установлен на въезде в военный городок. Именной борт в честь Героя Великой Отечественной войны Николая Даниленко. 611 ИАП был первым полком в СССР, который проводил войсковые испытания, освоение и принял на вооружение Су-15. Полк с ними заступил на боевое дежурство 30 октября 1968 г. На параде в Домодедово в 1967 г. летали Су-15 именно этого полка. На текущий момент, это наиболее полный фотообзор в сети ранней модификации Су-15. Самолет также полностью отснят сверху. Перейти к фотообзору",
            "Правила Совместной постройки (ГБ) – Самолет Lockheed P-38.Принимая во внимание приближающийся юбилей первого полета самолета P-38 и постоянный интерес к данному самолету, проводим данный конкурс. ",
            "Еще одна модель от Revell в 1/72. Изкоробка, добавил только антенны и поручни. Пытался сделать мигалку из прозрачного литника, но результат не устроил, и мигалка была оторвана)",
            "Предисловие. Планов собирать этого клопа в общем-то не было, но в параллель к основному проекту в качестве упражнения решил склеить, главным образом из-за декали, которая оказалась весьма плохого качества.",
            "Ну что, как и было обещано - между крупной деколью и деколью поменьше перерывчик небольшой. Выдана на гора деколь 72-067 Сухой Су-34. ",
            "Сегодня Андрей \"SilverGhost\" Кузнецов и Иван \"VMMaster\" Мешков представляют огромный совместный фотообзор первой серийной модификации истребителя-перехватчика Сухой Су-15 (НАТО - Flagon-A) б/н 01, серийный № 09-1, установленного на въезде в военный городок бывшей авиабазы Дорохово, Бежецк-5, Тверская область, Россия. Самолет выпущен примерно в 1968-1969 г., летал в составе 611 ИАП. При перевооружении полка на Су-27 в середине 1980-х годах, установлен на въезде в военный городок. Именной борт в честь Героя Великой Отечественной войны Николая Даниленко. 611 ИАП был первым полком в СССР, который проводил войсковые испытания, освоение и принял на вооружение Су-15. Полк с ними заступил на боевое дежурство 30 октября 1968 г. На параде в Домодедово в 1967 г. летали Су-15 именно этого полка. На текущий момент, это наиболее полный фотообзор в сети ранней модификации Су-15. Самолет также полностью отснят сверху. Перейти к фотообзору",
            "Правила Совместной постройки (ГБ) – Самолет Lockheed P-38.Принимая во внимание приближающийся юбилей первого полета самолета P-38 и постоянный интерес к данному самолету, проводим данный конкурс. "
    };


    public static List<PostEntity> generatePosts() {
        List<PostEntity> posts = new ArrayList<>(TITLE_NAME.length);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            PostEntity post = new PostEntity();
            post.setId(i);
            post.setStoryid(ONLINE_ID[i]);
            post.setTitle(TITLE_NAME[i]);
//            post.setAuthor(AUTHOR[i]);
            post.setThumbnailUrl(THUMBNAIL[i]);
//            post.setImagesUrls(IMAGES_URL[i]);
            post.setOriginalUrl(ORIGINAL_URL[i]);
//            post.setDate(DATE[i]);
//            post.setCategoryId(CATEGORY_ID[i]);
//            post.setCategoryName(CATEGORY_NAME[i]);
//            post.setLastUpdate(LAST_UPDATED[i]);
            post.setDescription(DESCRIPTION[i]);
            if (i == 0 || i == 1)
                post.setIsBookmark(true);
            posts.add(post);
        }
        return posts;
    }

    public static List<FeaturedEntity> generateFeatured() {
        List<FeaturedEntity> posts = new ArrayList<>(5);
//        for (int i = 0; i < 4; i++) {
//            FeaturedEntity post = new FeaturedEntity();
//            post.setId(i);
////            post.setOnlineId(ONLINE_ID[i]);
//            post.setTitle(TITLE_NAME[i]);
//            post.setAuthor(AUTHOR[i]);
//            post.setThumbnailUrl(THUMBNAIL[i]);
//            post.setImagesUrls(IMAGES_URL[i]);
//            post.setOriginalUrl(ORIGINAL_URL[i]);
//            post.setDate(DATE[i]);
//            post.setCategoryId(CATEGORY_ID[i]);
//            post.setCategoryName(CATEGORY_NAME[i]);
//            post.setLastUpdateDate(LAST_UPDATED[i]);
//            post.setDescription(DESCRIPTION[i]);
//            if (i == 0 || i == 1)
//                post.setFavorite(true);
//            posts.add(post);
//        }
        return posts;
    }

}