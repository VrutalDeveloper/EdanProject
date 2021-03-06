package tech.ankainn.edanapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.dto.FormTwoComplete;
import tech.ankainn.edanapplication.model.dto.FormTwoSubset;

@Dao
public abstract class FormTwoDao {

    @Query("SELECT form_two_id, form_two_api_id, data_version, department, province, district, date_creation, hour_creation, lot FROM form_two_table WHERE user_owner_id = :userId ORDER BY form_two_id DESC")
    public abstract LiveData<List<FormTwoSubset>> loadAllFormTwoSubset(long userId);

    @Transaction
    @Query("SELECT * FROM form_two_table WHERE form_two_id = :formTwoId")
    public abstract FormTwoComplete loadFormTwoById(long formTwoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertFormTwo(FormTwoData formTwoData);

    @Update
    public abstract void updateFormTwo(FormTwoData formTwoData);

    @Query("DELETE FROM form_two_table WHERE form_two_id = :formTwoId")
    public abstract void deleteFormTwo(long formTwoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertMember(MemberData memberData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertAllMembers(List<MemberData> memberDataList);

    @Update
    public abstract void updateMember(MemberData memberData);

    @Delete
    public abstract void deleteMember(MemberData memberData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertLivelihood(LivelihoodData livelihoodData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAllLivelihoods(List<LivelihoodData> livelihoodDataList);

    @Update
    public abstract void updateLivelihood(LivelihoodData livelihoodData);

    @Transaction
    public void insertFormTwoComplete(FormTwoData formTwoData) {

        final long formTwoId = insertFormTwo(formTwoData);

        List<MemberData> memberDataList = new ArrayList<>(formTwoData.memberDataList);
        for (MemberData memberData : formTwoData.memberDataList) {
            if (memberData.toRemove) {
                memberDataList.remove(memberData);
            } else {
                memberData.formTwoOwnerId = formTwoId;
            }
        }
        final long[] memberIds = insertAllMembers(memberDataList);

        for (int i = 0; i < memberIds.length; i++) {
            List<LivelihoodData> livelihoodDataList = memberDataList.get(i).livelihoodDataList;
            for (LivelihoodData livelihoodData : livelihoodDataList) {
                livelihoodData.formTwoOwnerId = formTwoId;
                livelihoodData.memberOwnerId = memberIds[i];
            }
            insertAllLivelihoods(livelihoodDataList);
        }
    }

    @Transaction
    public void updateFormTwoComplete(FormTwoData formTwoData) {

        updateFormTwo(formTwoData);

        List<MemberData> memberDataList = formTwoData.memberDataList;
        for (MemberData memberData : memberDataList) {

            if (memberData.toRemove) {
                if (memberData.id != 0L) {
                    deleteMember(memberData);
                }
                continue;
            }

            long memberId;
            if(memberData.id == 0L) {
                memberData.formTwoOwnerId = formTwoData.id;
                memberId = insertMember(memberData);
            } else {
                updateMember(memberData);
                memberId = memberData.id;
            }

            for (LivelihoodData livelihoodData : memberData.livelihoodDataList) {
                if (livelihoodData.id == 0L) {
                    livelihoodData.formTwoOwnerId = formTwoData.id;
                    livelihoodData.memberOwnerId = memberId;
                    insertLivelihood(livelihoodData);
                } else {
                    updateLivelihood(livelihoodData);
                }
            }
        }
    }
}
